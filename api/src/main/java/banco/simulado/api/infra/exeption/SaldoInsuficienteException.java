package banco.simulado.api.infra.exeption;

public class SaldoInsuficienteException extends Exception {

    public SaldoInsuficienteException(String msg) {
        super(msg);
    }
}
