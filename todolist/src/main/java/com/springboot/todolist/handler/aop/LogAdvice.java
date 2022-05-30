package com.springboot.todolist.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAdvice {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogAdvice.class);
	
	// 해당 메소드 호출시 걸리는 시간 출력
	//@Around("within(com.springboot.study.test..*)") // 해당 패키지 안 모든 클래스에 해당
	@Around("within(com.springboot.todolist..*)") // 해당 패키지 안 모든 클래스에 해당
	public Object logging(ProceedingJoinPoint pjp)  throws Throwable {
		
		long startAt = System.currentTimeMillis();
		Map<String, Object> params = getParams(pjp);
		
		LOGGER.info("---Advice Call : {}({}) = {}",pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName(), params); // 각 중괄호에 들어갈 값들 차례로 나열

		Object result = pjp.proceed(); // 지정된 메소드 실행
		
		long endAt = System.currentTimeMillis();

		LOGGER.info("---Advice End : {}({}) = {}({}ms 소요됨)",pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName(), result, endAt - startAt);
		return result;
	}
	
	public Map<String, Object> getParams(ProceedingJoinPoint pjp) {
		Map<String, Object> params = new HashMap<String, Object>();
		Object[] args = pjp.getArgs();
		String[] argsNames = ((CodeSignature) pjp.getSignature()).getParameterNames();
		
		for (int i=0; i<args.length; i++) {
			params.put(argsNames[i], args[i]);
		}
		return params;
	}
}