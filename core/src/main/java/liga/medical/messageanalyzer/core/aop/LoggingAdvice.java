package liga.medical.messageanalyzer.core.aop;

import liga.medical.common.dto.LogType;
import liga.medical.common.dto.MessageType;
import liga.medical.common.dto.RabbitMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoggingAdvice {

    /*private final long SYSTEM_TYPE_ID = 1;

    private final LoggingService loggingService;

    public LoggingAdvice(LoggingService loggingService) {
        this.loggingService = loggingService;
    }*/

    private void logMessage(LogType logType, String className, String methodName, Object[] args) {
        /*loggingService.logMessage(logType, SYSTEM_TYPE_ID,
                "Class: " + className + ", method: " + methodName + ", args: " + Arrays.toString(args));*/
        log.info("LogType: " + logType.toString() + " Class: " + className + ", method: " + methodName + ", args: " + Arrays.toString(args));
    }

    @Pointcut("@annotation(liga.medical.messageanalyzer.core.annotations.DBLog)")
    public void dbLog() {

    }

    @Around("dbLog()")
    public Object dbLogger(ProceedingJoinPoint pjp) {
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();

        Object obj = null;
        MessageType messageType = null;
        try {
            obj = pjp.proceed();
            messageType = ((RabbitMessageDTO)args[0]).getType();
        } catch (Throwable throwable) {
            logMessage(LogType.EXCEPTION, className, methodName, args);
            throwable.printStackTrace();
        }

        switch (messageType) {
            case ALERT:
            case DAILY:
                logMessage(LogType.DEBUG, className, methodName, args);
                break;
            default:
                logMessage(LogType.EXCEPTION, className, methodName, args);
        }

        return obj;
    }
}
