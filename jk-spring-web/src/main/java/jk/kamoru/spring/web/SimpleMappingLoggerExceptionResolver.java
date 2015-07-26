package jk.kamoru.spring.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * {@link SimpleMappingExceptionResolver} 상속받아 exception을 log.error로 남기는 기능 추가
 * <pre>
 * &lt;beans:bean id="exceptionResolver" class="{@link jk.kamoru.spring.web.SimpleMappingLoggerExceptionResolver}"&gt;
 *   &lt;beans:property name="exceptionMappings"&gt;
 *     &lt;beans:props&gt;
 *       &lt;beans:prop key="jk.kamoru.crazy.video.VideoException"&gt;error/videoError&lt;/beans:prop&gt;
 *     &lt;/beans:props&gt;
 *   &lt;/beans:property&gt;
 *   &lt;beans:property name="exceptionAttribute" value="exception"/&gt;
 *   &lt;beans:property name="defaultErrorView" value="error/defaultError"/&gt;
 * &lt;/beans:bean&gt;
 * </pre>
 * @author kamoru
 */
@Slf4j
public class SimpleMappingLoggerExceptionResolver extends SimpleMappingExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		
		// for Tomcat client abort exception
		if ("org.apache.catalina.connector.ClientAbortException".contains(ex.getClass().getName()) ) {
			log.error("Error - {}", ex.getMessage());
		}
		else {
			log.error("Error - ", ex);
		}
		return super.doResolveException(request, response, handler, ex);
	}

}
