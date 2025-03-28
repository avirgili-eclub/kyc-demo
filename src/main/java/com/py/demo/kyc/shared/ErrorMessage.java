package com.py.demo.kyc.shared;

import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ErrorMessage {
    private String code;
    private List<Map<String, String>> messages;

    public static ErrorMessage formatMessages(String code, List<FieldError> result) {
        List errors = result.stream().map((e) -> {
            HashMap error = new HashMap();
            error.put(e.getField(), e.getDefaultMessage());
            return error;
        }).collect(Collectors.toList());
        return builder().code(code).messages(errors).build();
    }

    public ErrorMessage(final String code, final List<Map<String, String>> messages) {
        this.code = code;
        this.messages = messages;
    }

    public static ErrorMessageBuilder builder() {
        return new ErrorMessageBuilder();
    }

    public String getCode() {
        return this.code;
    }

    public List<Map<String, String>> getMessages() {
        return this.messages;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setMessages(final List<Map<String, String>> messages) {
        this.messages = messages;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ErrorMessage)) {
            return false;
        } else {
            ErrorMessage other = (ErrorMessage)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$code = this.getCode();
                Object other$code = other.getCode();
                if (this$code == null) {
                    if (other$code != null) {
                        return false;
                    }
                } else if (!this$code.equals(other$code)) {
                    return false;
                }

                Object this$messages = this.getMessages();
                Object other$messages = other.getMessages();
                if (this$messages == null) {
                    return other$messages == null;
                } else return this$messages.equals(other$messages);
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ErrorMessage;
    }

    public int hashCode() {
        int result = 1;
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $messages = this.getMessages();
        result = result * 59 + ($messages == null ? 43 : $messages.hashCode());
        return result;
    }

    public String toString() {
        String var10000 = this.getCode();
        return "ErrorMessage(code=" + var10000 + ", messages=" + this.getMessages() + ")";
    }

    public static class ErrorMessageBuilder {
        private String code;
        private List<Map<String, String>> messages;

        ErrorMessageBuilder() {
        }

        public ErrorMessageBuilder code(final String code) {
            this.code = code;
            return this;
        }

        public ErrorMessageBuilder messages(final List<Map<String, String>> messages) {
            this.messages = messages;
            return this;
        }

        public ErrorMessage build() {
            return new ErrorMessage(this.code, this.messages);
        }

        public String toString() {
            return "ErrorMessage.ErrorMessageBuilder(code=" + this.code + ", messages=" + this.messages + ")";
        }
    }
}
