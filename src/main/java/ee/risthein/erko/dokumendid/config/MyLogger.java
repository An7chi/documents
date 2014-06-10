package ee.risthein.erko.dokumendid.config;

import com.google.common.base.Objects;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.Iterables.getLast;

/**
 * @author Erko Risthein
 */
@Aspect
@Component
public class MyLogger {

    @Before("execution(* ee.risthein.erko.dokumendid..*.*(..))")
    public void logControllerAccess(JoinPoint joinPoint) {
        printClassName(joinPoint);
        printMethodName(joinPoint);
        printArguments(joinPoint);
    }

    private void printClassName(JoinPoint joinPoint) {
        System.out.print(getClassName(joinPoint) + ".");
    }

    private void printMethodName(JoinPoint joinPoint) {
        System.out.print(joinPoint.getSignature().getName() + "(");
    }

    private void printArguments(JoinPoint joinPoint) {
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        for (Object arg : args) {
            System.out.print(stripLinebreaks(arg));
            if (!Objects.equal(arg, getLast(args))) {
                System.out.print(", ");
            }
        }
        System.out.println(")");
    }

    private String stripLinebreaks(Object arg) {
        return (arg == null) ? "null" : arg.toString().replace("\n", "").replace("\r", "");
    }

    private String getClassName(JoinPoint joinPoint) {
        Class aClass = joinPoint.getTarget().getClass();
        return isJpaRepository(aClass) ? getInterfaceName(aClass) : aClass.getSimpleName();
    }

    private String getInterfaceName(Class aClass) {
        return aClass.getInterfaces()[0].getSimpleName();
    }

    private boolean isJpaRepository(Class clazz) {
        return clazz.getSimpleName().startsWith("$");
    }

}