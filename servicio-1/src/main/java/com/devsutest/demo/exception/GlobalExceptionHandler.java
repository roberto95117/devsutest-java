package com.devsutest.demo.exception;

import com.devsutest.demo.model.RespuestaHttp;
import org.hibernate.JDBCException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespuestaHttp> handleException(Exception ex){
        RespuestaHttp res = new RespuestaHttp();
        res.setCode(500);
        if(ex.getCause() instanceof SQLException) {
            SQLException sqlEx = (SQLException)ex.getCause();
            res.setMsj(sqlEx.getMessage());
        }else if(ex.getCause() instanceof JDBCException) {
            JDBCException jdbcEx = (JDBCException)ex.getCause();
            SQLException sqlEx = jdbcEx.getSQLException();
            res.setMsj(sqlEx.getMessage());
        }else{
            res.setMsj(ex.getMessage());
        }
        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(EmptyResultDataAccessException.class)
    ResponseEntity<RespuestaHttp> sinRegistros() {

        RespuestaHttp res = new RespuestaHttp();

        res.setCode(404);
        res.setMsj("Sin registros - No se encontraron registros de la consulta realizada");

        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(NoEncontradoExc.class)
    ResponseEntity<RespuestaHttp> noEncontradoHandl(NoEncontradoExc ex) {

        RespuestaHttp res = new RespuestaHttp();

        res.setCode(404);
        res.setMsj(ex.getMessage());

        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(BadRequestExp.class)
    ResponseEntity<RespuestaHttp> badRequestHandl(BadRequestExp ex) {

        RespuestaHttp res = new RespuestaHttp();

        res.setCode(404);
        res.setMsj(ex.getMessage());

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
}