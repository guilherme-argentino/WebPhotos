package webfotos.gui;

import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import org.apache.log4j.Logger;

/**
 * Gera um calendário.
 * Usa um GridLayout para fazer o formato.
 * Pode formatar a data em dois tipos diferentes.
 */
public class Calendario extends JPanel {

    private String[] dias = {"dom", "seg", "ter", "qua", "qui", "sex", "sab"};
    private String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    private int[] diasNoMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private GregorianCalendar calendario = new GregorianCalendar();
    
    private Logger log = Logger.getLogger(Calendario.class);

    /**
     * Construtor da classe.
     * Não recebe parâmetros.
     * Simplesmente seta a data atual.
     */
    public Calendario() {
        calendario.setTime(new Date());
    }

    /**
     * Construtor da classe.
     * Recebe o formato da data como parâmetro. Formato dd/mm/aa ou dd/mm/aaaa.
     * Inicia um objeto Date e checa o tamanho da String recebida, para armazenar a data no formato pedido.
     * Após descobrir o tipo selecionado, seta o valor do calendario.
     * @param formato_ddmmaa Formato da data.
     */
    public Calendario(String formato_ddmmaa) {
        Date data = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        if (formato_ddmmaa.length() == 10) {
            sdf.applyPattern("dd/MM/yyyy");
        }

        try {
            data = sdf.parse(formato_ddmmaa);
        } catch (Exception e) {
            log.warn("Erro na conversão de data: " + formato_ddmmaa, e);
            data = new Date();
        }
        calendario.setTime(data);
    }

    /**
     * Imprime a saída com os dados da data, montando um calendário em um {@link java.awt.GridLayout GridLayout}.
     * Apresentando dados como ano, mês, dia, número de dias no mês e primeiro dia da semana.
     */
    public void mostrar() {
        int indiceMes = calendario.get(Calendar.MONTH); // começa 0-Janeiro, 1-Fevereiro...
        int ano = calendario.get(Calendar.YEAR);
        // ano é bissexto ?
        if (calendario.isLeapYear(ano)) {
            diasNoMes[1] = 29;
        }

        String nomeMes = meses[indiceMes];
        int numDias = diasNoMes[indiceMes];

        calendario.set(Calendar.DATE, 1);
        int numPrimeiroDiaMes = calendario.get(Calendar.DAY_OF_WEEK);

        int numLinhas = (numDias + numPrimeiroDiaMes - 1) / 7;
        float resto = (numDias + numPrimeiroDiaMes - 1) % 7;
        if (resto > 0) {
            numLinhas++;
        }

        log.debug(nomeMes + " " + ano);
        log.debug("numero de dias do mês: " + numDias);
        log.debug("primeiro dia da semana: " + numPrimeiroDiaMes);
        log.debug("Linhas: " + numLinhas);

        setLayout(new GridLayout(numLinhas + 1, 7, 1, 1));
        // adiciona ao grid o nome dos dias
        for (int i = 0; i < 7; i++) {
            add(new JLabel(dias[i]));
        }

        // adiciona os dias "vazios"
        if (numPrimeiroDiaMes > 1) {
            for (int i = 0; i < numPrimeiroDiaMes - 1; i++) {
                add(new JLabel(""));
            }
        }
        // adiciona os dias
        for (int i = 0; i < numDias; i++) {
            add(new JToggleButton(Integer.toString(i + 1)));
        }

        setVisible(true);
    }
}
