package src.services;

import java.util.ArrayList;
import java.util.List;

import src.entities.Registro;

public class HashingEstendido 
{
    public class Diretorio 
    {
    
        private List<Bucket> indices; //ponteiros para os buckets
        int p;  //Profundidade
    
        public Diretorio(int profundidadeInicial) 
        {
            this.p = profundidadeInicial;
            this.indices = new ArrayList<>();
            Bucket bucketInicial = new Bucket(1); 
            this.indices.add(bucketInicial);
            this.indices.add(bucketInicial);
        }
    
        public Bucket obterBucket(int indice) 
        {
            return indices.get(indice);
        }
    
        public void dobrar() //dobra o tamanho do diretorio
        {
            int tamanhoAntigo = indices.size();
            for (int i = 0; i < tamanhoAntigo; i++) {
                indices.add(indices.get(i));
            }
            p++;
        }
        
        public void atualizarIndice(int indice, Bucket novoBucket) 
        {
            indices.set(indice, novoBucket);
        }
        
        public int getProfundidade() 
        {
            return p;
        }
    }
    
    class Bucket {
        private List<Registro> registros;
        int profundidadeLocal;
        private final int tam;
    
        public Bucket(int profundidadeLocal) 
        {
            this.profundidadeLocal = profundidadeLocal;
            this.registros = new ArrayList<>();
            this.tam = 418; //5% do tamanho da bd;
        }

        public boolean estaCheio() {
            return registros.size() >= tam;
        }

        public void inserir(Registro registro) 
        {
            registros.add(registro);
        }

        public Registro buscar(int id)
        { 
        
            for (Registro registro : registros) 
            {
                if (registro.getPlayer().getId() == id) 
                {
                    return registro;
                }
            }
            return null;  // Não encontrado
        }

        public List<Registro> getRegistros() 
        {
            return registros;
        }

        public void limpar() 
        {
            registros.clear();
        }

        public boolean remover(int id) 
        {
            for (int i = 0; i < registros.size(); i++) {
                if (registros.get(i).getPlayer().getId() == id) {
                    registros.remove(i);
                    // Atualiza a base de dados e o arquivo de índices...
                    return true;
                }
            }
            return false;  // Não encontrado
        }


    }

    private Diretorio diretorio;

    public HashingEstendido() 
    {
        diretorio = new Diretorio(1);  // Iniciando com profundidade 1
    }

    public void inserir(Registro registro) 
    {
        int hash = hash(registro.getPlayer().getId(), diretorio.getProfundidade());
        Bucket bucket = diretorio.obterBucket(hash);
        if (bucket.estaCheio()) {
            if (bucket.profundidadeLocal == diretorio.getProfundidade()) {
                diretorio.dobrar();
            }
            dividirBucket(hash);
            // Tenta inserir novamente
            inserir(registro);
        } else {
            bucket.inserir(registro);
        }
    }

    public boolean remover(int id) 
    {
        int hash = hash(id, diretorio.getProfundidade());
        Bucket bucket = diretorio.obterBucket(hash);
        return bucket.remover(id);
    }

    int hash(int k, int p) 
    {
        return k % (1 << p);
    }
    
    public Registro buscar(int id) 
    {
        int hash = hash(id, diretorio.getProfundidade());
        Bucket bucket = diretorio.obterBucket(hash);
        return bucket.buscar(id);
    }

    private void dividirBucket(int indice) 
    {
        Bucket bucketAntigo = diretorio.obterBucket(indice);
        Bucket novoBucket = new Bucket(bucketAntigo.profundidadeLocal + 1);

        List<Registro> registros = bucketAntigo.getRegistros();
        bucketAntigo.limpar();  // Limpa o bucket antigo

        for (Registro registro : registros) 
        {
            int hash = hash(registro.getPlayer().getId(), diretorio.getProfundidade());
            Bucket bucket = diretorio.obterBucket(hash);
            bucket.inserir(registro);
        }

        int novoIndice = indice + (1 << (diretorio.getProfundidade() - 1));
        diretorio.atualizarIndice(novoIndice, novoBucket);
    }
        
}
