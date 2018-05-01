package ${groupId}.support;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class ErrorController {
    private static final Logger log = LoggerFactory.getLogger(ErrorController.class);
    protected static final String KEINE_MESSAGE = "keine Message";
    protected static final String TRACE_ID = "traceId";
    private DateTimeFormatter dateTimeXmlFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
    private Boolean skipStacktraceForTest = false;

    public void setSkipStacktraceForTest(Boolean skipStacktraceForTest) {
        this.skipStacktraceForTest = skipStacktraceForTest;
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<ResponseMessage> defaultHandlerException(Exception exception) {
        String message = extractMessage(exception);

        if (skipStacktraceForTest) {
            log.error(message);
        } else {
            log.error(message, exception);
        }

        return buildResponseEntity(message, BAD_REQUEST);
    }

    private ResponseEntity<ResponseMessage> buildResponseEntity(String message, HttpStatus httpStatus) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage(message);
        responseMessage.setStatusCode(httpStatus.value());
        responseMessage.setStatusMessage(httpStatus.getReasonPhrase());
        responseMessage.setTimestamp(dateTimeXmlFormat.print(LocalDateTime.now()));
        responseMessage.setTraceId(MDC.get(TRACE_ID));

        return new ResponseEntity<>(responseMessage, httpStatus);
    }

    private String extractMessage(Throwable throwable) {
        if (throwable.getMessage() != null)
            return throwable.getMessage();

        if (throwable.getCause() != null)
            return extractMessage(throwable.getCause());

        return KEINE_MESSAGE;
    }
}

