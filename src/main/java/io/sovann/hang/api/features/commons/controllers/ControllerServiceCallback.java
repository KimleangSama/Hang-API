package io.sovann.hang.api.features.commons.controllers;

import io.sovann.hang.api.exceptions.*;
import io.sovann.hang.api.features.commons.payloads.*;
import lombok.extern.slf4j.*;
import org.hibernate.exception.*;
import org.springframework.dao.*;
import org.springframework.stereotype.*;

@Slf4j
@Component
public class ControllerServiceCallback {
    private static final String LOG_ERROR = "{}: {}";

    public <T> BaseResponse<T> execute(ServiceOperation<T> operation, String errorMessage, PageMeta page) {
        try {
            T result = operation.execute();
            return BaseResponse.<T>ok().setPayload(result).setMetadata(page);
        } catch (ResourceNotFoundException e) {
            log.error(LOG_ERROR, errorMessage, e.getMessage(), e);
            return BaseResponse.<T>notFound().setErrors(e.getMessage());
        } catch (ResourceForbiddenException e) {
            log.error(LOG_ERROR, errorMessage, e.getMessage(), e);
            return BaseResponse.<T>accessDenied().setErrors(e.getMessage());
        } catch (ResourceDeletedException | ConstraintViolationException e) {
            log.error(LOG_ERROR, errorMessage, e.getMessage(), e);
            return BaseResponse.<T>badRequest().setErrors(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            String getMessage = "Duplicate entity on unique field with details: " + e.getMessage();
            log.error(LOG_ERROR, errorMessage, getMessage, e);
            return BaseResponse.<T>duplicateEntity().setErrors(getMessage);
        } catch (Exception e) {
            log.error(LOG_ERROR, errorMessage, e.getMessage(), e);
            return BaseResponse.<T>exception().setErrors(e.getMessage());
        }
    }

    @FunctionalInterface
    public interface ServiceOperation<T> {
        T execute() throws ResourceException;
    }
}
