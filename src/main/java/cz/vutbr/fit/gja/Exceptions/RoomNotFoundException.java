package cz.vutbr.fit.gja.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RoomNotFoundException extends RuntimeException {
    private String fieldName;
    private Object fieldValue;

    public RoomNotFoundException(String fieldName, Object fieldValue) {
        super(String.format("Místnost s %s '%s' nebyla nalezena!", fieldName, fieldValue));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
