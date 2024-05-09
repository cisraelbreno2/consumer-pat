package br.com.alelo.consumer.consumerpat.exception.standard;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.Date;

public abstract class StandardRestHandler {
    private final Log logger;

    protected StandardRestHandler() {
        this.logger = LogFactory.getLog(getClass());
    }

    private void logHandledException(Exception e, HttpServletRequest request) {
        this.logger.error(
                "Handled '" + e.toString() + "' " +
                        "AT: '" + request.getRequestURI() + "' " +
                        "FROM: '" + request.getRemoteAddr() + "/" + request.getRemoteHost() + "'");
        this.logger.error(e.getMessage(), e);
    }


    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<StandardResponseError> handleException(HttpServletRequest request, IllegalArgumentException iae) {
        StandardResponseError res = defaultHandling(request, iae);
        res.setException(iae.getClass().getName());
        res.setMessage(iae.getMessage());
        return resolveResponse(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ ResourceAccessException.class })
    public ResponseEntity<StandardResponseError> handleException(HttpServletRequest request, ResourceAccessException rae) {
        StandardResponseError res = defaultHandling(request, rae);
        res.setMessage("Um recurso interno requisitado não está disponível.");
        return resolveResponse(res, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<StandardResponseError> handleException(HttpServletRequest request, MethodArgumentTypeMismatchException mat) {
        StandardResponseError res = defaultHandling(request, mat);
        res.setMessage(mat.getMessage());
        return resolveResponse(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ MissingServletRequestParameterException.class })
    public ResponseEntity<StandardResponseError> handleException(HttpServletRequest request, MissingServletRequestParameterException e) {
        StandardResponseError res = defaultHandling(request, e);
        res.setMessage(String.format("O 'Query Param' necessário '%s' não está presente", e.getParameterName()));
        return resolveResponse(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ HttpMessageNotReadableException.class })
    public ResponseEntity<StandardResponseError> handleException(HttpServletRequest request, HttpMessageNotReadableException hmn) {
        StandardResponseError res = defaultHandling(request, hmn);
        res.setMessage(hmn.getMessage());
        return resolveResponse(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<StandardResponseError> handleException(HttpServletRequest request, AccessDeniedException ade) {
        StandardResponseError res = defaultHandling(request, ade);
        res.setMessage(ade.getMessage());
        return resolveResponse(res, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<StandardResponseError> handleException(HttpServletRequest request, RuntimeException e) {
        StandardResponseError res = defaultHandling(request, e);
        res.setMessage(e.getMessage());
        return resolveResponse(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<StandardResponseError> handleException(HttpServletRequest request, Exception e) {
        StandardResponseError res = defaultHandling(request, e);
        res.setMessage(e.getMessage());
        return resolveResponse(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private StandardResponseError defaultHandling(HttpServletRequest request, Exception e) {
        this.logHandledException(e, request);
        var res = new StandardResponseError();
        res.setError(true);
        res.setException(e.getClass().getSimpleName());
        res.setMethod(request.getMethod());
        res.setPath(request.getRequestURI());
        res.setTimestamp(new Date());
        return res;
    }

    private static ResponseEntity<StandardResponseError> resolveResponse(StandardResponseError res, HttpStatus status) {
        res.setStatus(String.valueOf(status.value()));
        return ResponseEntity
                .status(status)
                .body(res);
    }

}