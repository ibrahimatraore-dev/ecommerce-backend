package alten.api.aop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Intercepte puis loggue tous les paramètres d'entrées des méthodes présentes dans une classe annotée par {@link org.springframework.web.bind.annotation.RestController}.
 */
@Aspect
@Configuration
public class InternalApiLogging {

    private static final Logger LOGGER = LoggerFactory.getLogger(InternalApiLogging.class);

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerPontcut() {

    }

    @Before(value = "restControllerPontcut()")
    public void before(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String method = joinPoint.getSignature().getName();
        if("login".equals(method)){
            return;
        }
        String methodParameters = Arrays.toString(joinPoint.getArgs());

        if (className.startsWith("springfox")) {
            return;
        }

        LOGGER.info("{} - Entry method: {}", className, method);
        LOGGER.info("{} - Entry method parameters: {}", className, methodParameters);
    }
}
