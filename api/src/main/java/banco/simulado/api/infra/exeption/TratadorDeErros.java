package banco.simulado.api.infra.exeption;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    } //404

//    ===============================================================================================================

    // tratar erros de validação // mais de um por isto uma lista
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

//     pega a minha exception e joga no corpo da requisição este erro
    @ExceptionHandler(ItemJaExisteException.class)
    public ResponseEntity tratarError400Existe(ItemJaExisteException ex){
        var erros = ex.getMessage();
        return ResponseEntity.badRequest().body(erros);
    }

    //     pega a minha exception e joga no corpo da requisição este erro
    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity tratarError400Insuficiente(SaldoInsuficienteException ex){
        var erros = ex.getMessage();
        return ResponseEntity.badRequest().body(erros);
    }

    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) { // construtor para o map
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

//    ===============================================================================================================
}
