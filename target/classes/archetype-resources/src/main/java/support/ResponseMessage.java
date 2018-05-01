package ${groupId}.support;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.http.HttpStatus;

public class ResponseMessage {
    private DateTimeFormatter dateTimeXmlFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
    private String message;
    private Integer statusCode;
    private String statusMessage;
    private String timestamp;
    private String traceId;

    public ResponseMessage() {
    }

    public ResponseMessage(String message, String traceId, HttpStatus httpStatus) {
        this.message = message;
        this.traceId = traceId;
        this.statusCode = httpStatus.value();
        this.statusMessage = httpStatus.getReasonPhrase();
        this.timestamp = dateTimeXmlFormat.print(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "message='" + message + '\'' +
                ", statusCode=" + statusCode +
                ", statusMessage='" + statusMessage + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", traceId='" + traceId + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
