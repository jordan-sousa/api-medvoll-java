package med.voll.api.infra;

import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity tratarErro400(MethodArgumentNotValidException exception) {
//        var erros =  exception.getFieldError();
//
//        return ResponseEntity.badRequest().body(erros.wrap().map(DadosErroValidacao::new).toList());
//    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> tratarErro400(MethodArgumentNotValidException exception) {
        var erros = exception.getFieldErrors();  // Obtém todos os erros de campo
        var errosValidacao = erros.stream()      // Cria um stream dos erros
                .map(DadosErroValidacao::new)    // Mapeia cada erro para DadosErroValidacao
                .toList();                       // Coleta os resultados em uma lista

        return ResponseEntity.badRequest().body(errosValidacao);  // Retorna a lista de erros
    }


    private  record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
