package TrabalhoPOO.TranalhoPOO;

import java.time.LocalDate;

import model.Conta;
import model.TipoReceita;
import model.TiposDescontos;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Conta conta = new Conta("Eduardo", 123123, 1000);
    	
    	conta.novaReceita(1688, LocalDate.of(2026, 6, 1), TipoReceita.SALARIO);
    	conta.novaReceita(1688, LocalDate.of(2026, 7, 1), TipoReceita.SALARIO);
    	conta.novaReceita(1688, LocalDate.of(2026, 8, 1), TipoReceita.SALARIO);
    	conta.novaReceita(1688, LocalDate.of(2026, 7, 20), TipoReceita.SALARIO);
    	conta.novoDesconto(3000, LocalDate.of(2026, 8, 1), TiposDescontos.ENTRETENIMENTO);
    	
    	System.out.println(conta.calcularSaldoData(LocalDate.of(2026, 9, 1)));
    }
}
