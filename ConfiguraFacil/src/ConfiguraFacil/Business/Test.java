package ConfiguraFacil.Business;

import ConfiguraFacil.Business.Exceptions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Test{

    public static void main(String[] args) throws ComponenteIncompativelException, ComponenteObrigatorioException, TipoDeComponenteException, ComponenteInexistenteException, ComponenteExistenteException, ComponenteNaoPodeSerRemovidoException, PacoteInexistenteNaConfiguracaoException {
    //Processo:
    //Cria configuração(estado 0) -> Insere Componentes/Pacotes -> método confirmaConfig()(estado 1)
    //método addQueue() envia a config para a lista de espera da fábrica(estado 2)
    //método produzCarros() produz todos os carros possíveis com o stock atual e as configs passam para o estado 3
        /*
        List<Componente> inc = new ArrayList<>();
        List<Componente> obr = new ArrayList<>();
        List<Componente> comp = new ArrayList<>();
        List<Componente> componentes = new ArrayList<>();
        List<Pacote> pacotes = new ArrayList<>();
        List<Cliente> clientes = new ArrayList<>();
        Map<String,Integer> stock = new HashMap<>();
       
        Cliente c1 = new Cliente("abc","a","Paulo","adad@damdad");
        Funcionario f1 = new Funcionario("adada","a","Manuel","adnanda@");
    	Componente a = new Componente("1","carro","dadajdna",(double)350);
        Componente b = new Componente("2","pintura","Ajustes de pintura",(double)100);
        Componente c = new Componente("3","motor","dada",(double)300);
        Componente d = new Componente("4","jantes","Pintura verde",(double)300);
        Componente e = new Componente("5","pneus","dadajdna",(double)350);
        Componente f = new Componente("6","vidros","dadajdna",(double)350);
        Componente g = new Componente("7","para-choques","dadajdna",(double)350);
        Componente h = new Componente("8","estofos","dadajdna",(double)350);
        Componente i = new Componente("9","Motor","dadajdna",(double)350);
        componentes.add(a);componentes.add(b);componentes.add(c);componentes.add(d);componentes.add(e);
        componentes.add(f);componentes.add(g);componentes.add(h);
        
        Pacote p1 = new Pacote("1","Sport",250,componentes);
        pacotes.add(p1);
        Configuracao conf = new Configuracao("1");
        Configuracao conf2 = new Configuracao("2;");
        
        conf.addPacote(p1);
        conf2.addPacote(p1);
        conf.validaConfig(conf);
        conf2.validaConfig(conf2);
        //print de uma configuração
        //System.out.println(conf.toString());
        funcionarios.add(f1);clientes.add(c1);
        Stand s = new Stand("Tone",componentes,pacotes,funcionarios,clientes);
        //Coloca componentes no map stock e depois é adicionado ao stock da fábrica
        stock.put(a.getId(),1);stock.put(b.getId(),1);stock.put(c.getId(),1);stock.put(d.getId(),1);
        stock.put(e.getId(),1);stock.put(f.getId(),1);stock.put(g.getId(),1);stock.put(h.getId(),1);
        //Cria a fábrica
        Fabrica fa = new Fabrica("Peças",funcionarios,stock);

        fa.addQueue(conf);fa.addQueue(conf2);
        fa.produzCarros();
        fa.updateStock("1",4);
        System.out.println(fa.toString());
        //System.out.println(s.toString());  

        c1.addConfiguracao(conf);
        c1.addConfiguracao(conf2);
        //System.out.println(c1);*/
    }    
}