package cc.starxy.benchmarkspring.aspect;

import cc.starxy.benchmarkspring.config.HttpContextUtils;
import cc.starxy.benchmarkspring.config.IpUtils;
import cc.starxy.benchmarkspring.db.SysLogOperationEntity;
import cc.starxy.benchmarkspring.db.SysLogOperationService;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 操作日志，切面处理类
 * @author sdmq
 * @since 1.0.0
 */
@Aspect
@Component
public class LogOperationAspect {
	@Autowired
	private SysLogOperationService sysLogOperationService;

	/**
	 * 用于SpEL表达式解析.
	 */
	private SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

	/**
	 * 用于获取方法参数定义名字.
	 */
	private DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();


	// 拦截所有的web controller
	//@Pointcut("@annotation(com.qzsoft.jeemis.commons.log.annotation.LogOperation)" )
	@Pointcut("@annotation(LogOperation)")
	public void logPointCut() {

	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		try {
			//执行方法
			Object result = point.proceed();
			//执行时长(毫秒)
			long time = System.currentTimeMillis() - beginTime;
			String response = "";
			Long size = 0L;
			if (ObjectUtil.isNotNull(result)) {
				response = JSONUtil.toJsonStr(result);
				size = response.length() * 1L;
			}
			//保存日志
			saveLog(point, time, size, 1, response);
			return result;
		} catch (Exception e) {
			//执行时长(毫秒)
			long time = System.currentTimeMillis() - beginTime;
			//保存日志
			saveLog(point, time, 0L, 2, "");
			throw e;
		}
	}

	private void saveLog(ProceedingJoinPoint joinPoint, long time, Long size, Integer status, String response) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		SysLogOperationEntity log = new SysLogOperationEntity();
		LogOperation annotation = method.getAnnotation(LogOperation.class);
		if (annotation != null) {
			//注解上的描述
			log.setOperation(annotation.value());
			String serviceCode = annotation.serviceCode();
			if (StringUtils.isNotBlank(serviceCode) && (serviceCode.contains("#") || serviceCode.contains("T(")
					|| serviceCode.contains("$"))) {
				serviceCode = getValBySpEL(serviceCode, joinPoint);
			}
			log.setServiceCode(serviceCode);
			String tag = annotation.tag();
			log.setTag(tag);
		}

		// 客户端
        String appId = "123";
        log.setAppId(appId);
		//登录用户信息
        String username = "username123";
        if (StringUtils.isNotBlank(username)) {
			log.setCreatorName(username);
			log.setCreator("123123");
		}
		log.setModule("module");
		log.setStatus(status);
		log.setRequestTime(time);
		log.setRespondSize(size);
		log.setCreateDate(new Date());

		//请求相关信息
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		log.setIp(IpUtils.getIpAddr(request));
		log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
		log.setRequestUri(request.getRequestURI());
		log.setRequestMethod(request.getMethod());

		//请求参数
		Object[] args = joinPoint.getArgs();
		try {
			Object arg = args[0];
			String params;
			if(arg instanceof MultipartFile){
				// 如果请求参数是文件类型,就不记录相关内容了
				MultipartFile multipartFile = (MultipartFile)arg;
				params = "上传文件,文件名称:"+multipartFile.getOriginalFilename();
			}else{
				params = JSON.toJSONString(arg);
			}
			log.setRequestParams(params);
		} catch (Exception e) {

		}
		sysLogOperationService.save(log);
	}

	/**
	 * 解析spEL表达式
	 */
	private String getValBySpEL(String spEL, ProceedingJoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		//获取方法参数值
		Object[] args = joinPoint.getArgs();
		//获取方法形参名数组
		String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
		if (paramNames != null && paramNames.length > 0) {
			Expression expression = spelExpressionParser.parseExpression(spEL);
			// spring的表达式上下文对象
			StandardEvaluationContext context = new StandardEvaluationContext();
			// 给上下文赋值
			for (int i = 0; i < args.length; i++) {
				context.setVariable(paramNames[i], args[i]);
			}
			return expression.getValue(context).toString();
		}
		return null;
	}
}