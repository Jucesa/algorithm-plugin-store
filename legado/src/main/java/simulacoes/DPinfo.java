/*
 * 
 */
package simulacoes;

import dp.Const;
import dp.D;
import dp.Pattern;
import org.apache.commons.math3.stat.inference.ChiSquareTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author Tarcísio Lucas
 */
public class DPinfo {
    
    public static double WRAcc(Pattern p, Base b){
        double TP = p.getTP();
        double FP = p.getFP();
        double numeroExemplosPositivo = b.getNumeroExemplosPositivo();
        double numeroExemplos = b.getNumeroExemplos();
        
        if(TP == 0.0 && FP == 0.0){
            return 0.0;
        }
        
        double sup = (TP+FP) / numeroExemplos;
        double conf = TP / (TP+FP);
        double confD = numeroExemplosPositivo / numeroExemplos;

        return sup * ( conf  - confD);
    }
    
    public static double WRAccNormalized(Pattern p, Base b){
        double TP = p.getTP();
        double FP = p.getFP();
        double numeroExemplosPositivo = b.getNumeroExemplosPositivo();
        double numeroExemplos = b.getNumeroExemplos();
        
        if(TP == 0.0 && FP == 0.0){
            return 0.0;
        }
        
        double sup = (TP+FP) / numeroExemplos;
        double conf = TP / (TP+FP);
        double confD = numeroExemplosPositivo / numeroExemplos;
        double wracc = sup * ( conf  - confD);
         
        return 4 * wracc;
    }
    
    public static double Qg(Pattern p){
        double TP = p.getTP();
        double FP = p.getFP();
        return TP/(FP + 1.0);
    }
    
    public static double DiffSup(Pattern p, Base b){        
        double TP = p.getTP();
        double FP = p.getFP();
        double numeroExemplosPositivo = b.getNumeroExemplosPositivo();
        double numeroExemplosNegativo = b.getNumeroExemplosNegativo();
        
        double suppP = TP / numeroExemplosPositivo;
        double suppN = FP / numeroExemplosNegativo;

        return Math.abs(suppP - suppN);
    }
    
    public static double GrowthRate(Pattern p, Base b){        
        double TP = p.getTP();
        double FP = p.getFP();
        double numeroExemplosPositivo = b.getNumeroExemplosPositivo();
        double numeroExemplosNegativo = b.getNumeroExemplosNegativo();
        
        double suppP = TP / numeroExemplosPositivo;
        double suppN = FP / numeroExemplosNegativo;
        
        double valor;
        if(suppP == 0.0){
            valor = 0.0;
        }else if(suppN == 0.0){
            valor = Double.POSITIVE_INFINITY;
        }else{
            valor = suppP/suppN;
        }
        return valor;
    }
    
    public static double OddsRatio(Pattern p, Base b){        
        double TP = p.getTP();
        double FP = p.getFP();
        double numeroExemplosPositivo = b.getNumeroExemplosPositivo();
        double numeroExemplosNegativo = b.getNumeroExemplosNegativo();
        
        double suppP = TP / numeroExemplosPositivo;
        double suppN = FP / numeroExemplosNegativo;

        return ( suppP /(1-suppP) ) / ( suppN / (1-suppN) );
    }
    
    public static double lift(Pattern p, Base b){
        double TP = p.getTP();
        double FP = p.getFP();
        double supCond = (TP + FP) / (double)b.getNumeroExemplos(); //Suporte antecedente: número de exemplos da regra sobre o total |D|
        double supTarget = (double)b.getNumeroExemplosPositivo() / (double)b.getNumeroExemplos();  //Suporte consequente: número de exemplos com rótulo em relação ao total |Dp| / |D|
        double supDP = DPinfo.supp(p, b); //Suporte antecedente e consequente: count()

        return supDP / (supCond * supTarget);
    }
    
    public static double p_value(Pattern p, Base b){
         /*
        	D1	D2	Sum
        p	n11	n12	n1
        p_	n21	n22	n2
        Sum	|Dp|	|Dn|	|D|
        
        n11 = TP (positivos presentes na DP)
        n21 = |Dp| - TP (positivos não presentes na DP)
        n12 = FP (negativos presentes na DP)        
        n11 = |Dn| - FP (negativos ausêntes na DP)
        
        REF: Discriminative pattern mining and its applications in bioinformatics      
        
        */      
        
        //Só é preciso isso para calcular via função pronta!
        long[][] n = new long[2][2];
        n[0][0] = p.getTP();
        n[1][0] = b.getNumeroExemplosPositivo() - n[0][0];
        n[0][1] = p.getFP();
        n[1][1] = b.getNumeroExemplosNegativo() - n[0][1];
        ChiSquareTest chiTest = new ChiSquareTest();
        //Returns the observed significance level, or p-value, associated with a chi-square test of independence based on the input counts array, viewed as a two-way table.
        return chiTest.chiSquareTest(n);
    }
        
    public static double chi_quad(Pattern p, Base b){
        /*
        	D1	D2	Sum
        p	n11	n12	n1
        p_	n21	n22	n2
        Sum	|Dp|	|Dn|	|D|
        
        n11 = TP (positivos presentes na DP)
        n21 = |Dp| - TP (positivos não presentes na DP)
        n12 = FP (negativos presentes na DP)        
        n11 = |Dn| - FP (negativos ausêntes na DP)
        
        REF: Discriminative pattern mining and its applications in bioinformatics      
        
        */      
        
        //Só é preciso isso para calcular via função pronta!
        long[][] n = new long[2][2];
        n[0][0] = p.getTP();
        n[1][0] = b.getNumeroExemplosPositivo() - n[0][0];
        n[0][1] = p.getFP();
        n[1][1] = b.getNumeroExemplosNegativo() - n[0][1];
        
        ChiSquareTest chiTest = new ChiSquareTest();
        
        return chiTest.chiSquare(n);
    } 
    
    public static double supp(Pattern p, Base b){
        double TP = p.getTP();
        double numeroExemplos = b.getNumeroExemplos();

        return TP / numeroExemplos;
    }
    
    public static double suppPositivo(Pattern p, Base b){
        double TP = p.getTP();
        double numeroExemplosPositivo = b.getNumeroExemplosPositivo();

        return TP / numeroExemplosPositivo;
    }
    
    public static double suppNegativo(Pattern p, Base b){
        double FP = p.getFP();
        double numeroExemplosNegativo = b.getNumeroExemplosNegativo();

        return FP / numeroExemplosNegativo;
    }
       
    public static double cov(Pattern p, Base b){
        double TP = p.getTP();
        double FP = p.getFP();
        double numeroExemplos = b.getNumeroExemplos();

        return (TP + FP) / numeroExemplos;
    }
    
    public static double conf(Pattern p){
        double TP = p.getTP();
        double FP = p.getFP();

        return TP / (TP+FP);
    }   
    
    public static double size(Pattern p){
        return p.getItens().size();
    }

    public static double TP(Pattern p){
        return p.getTP();
    }
    
    public static double FP(Pattern p){
        return p.getFP();
    }
    
    public static double overallSuppPositive(Pattern[] P, Base b){
        
        double TPgrupo = 0;
        boolean[] vrpGrupo = new boolean[b.getNumeroExemplosPositivo()];

        for (Pattern pattern : P) {
            boolean[] vrpItem = pattern.getVrP();
            for (int j = 0; j < vrpItem.length; j++) {
                if (vrpItem[j]) {
                    vrpGrupo[j] = true;
                }
            }
        }

        for (boolean value : vrpGrupo) {
            if (value) {
                TPgrupo++;
            }
        }

        return TPgrupo /(double)vrpGrupo.length;
    } 
    
    
    public static double coverRedundancy(Pattern[] P, Base b){
        /*
        FONTE: Artigo Diverse subgroup set discovery, 2012 (DSSD)
        #Cover redundancy (CR)
        dataset S
        set of subgroups G
        cover count: c(t,G) 
                number of times t is covered by a subgroup in a subgroup set.

        expected cover count: c'(t,G)
                número de vezes que cada exemplo dividido pelo o total de exemplos.

        cover redundancy: CR(S,G) = { somaTodos[(c-c')/c'] } / |S|       
        */
        
        double numeroExemplosPositivos;
        if(b == null){
            numeroExemplosPositivos = D.numeroExemplosPositivo;
        }else{
            numeroExemplosPositivos = b.getNumeroExemplosPositivo();
        }
        
        double[] frequenciaD = new double[(int)numeroExemplosPositivos];
        double frequenciaEsperadaD = 0.0;
               
        //Preechendo frequenciaD
        for (Pattern pattern : P) {
            boolean[] vrp = pattern.getVrP();
            for (int j = 0; j < vrp.length; j++) {
                if (vrp[j]) {
                    frequenciaD[j]++;
                    frequenciaEsperadaD++;
                }
            }
        }
        
        //Calculando frequência esperada
        frequenciaEsperadaD = frequenciaEsperadaD / numeroExemplosPositivos;
        
        //Calculando Cover Redundancy
        double coverRedundancy = 0.0;
        for (double v : frequenciaD) {
            coverRedundancy += Math.abs(v - frequenciaEsperadaD) / frequenciaEsperadaD;
        }
        coverRedundancy = coverRedundancy / numeroExemplosPositivos;
               
        return coverRedundancy;    
    } 
    
    
    public static double descritionRedundancyDensity(Pattern[] P){
        /*
        Eu criei, não sei existe      
        DR1 = número itens únicos/número de itens
        */
        double descritionRedundancy1;
        
        double numeroItens = 0.0;
        HashSet<Integer> itensUnicos = new HashSet<>();

        for (Pattern pattern : P) {
            numeroItens += pattern.getItens().size();
            itensUnicos.addAll(pattern.getItens());
        }
        
        descritionRedundancy1 = itensUnicos.size() / numeroItens;
        
        return descritionRedundancy1;    
    }
    
    public static double descritionRedundancyDominator(Pattern[] P){
        /*
        Eu criei, não sei existe      
        DR2 = frequência que o item mais utilizado se repeta/k
        */
        
        //Capturando itens únicos nas top-k DPs
        HashSet<Integer> itensUnicosHS = new HashSet<>();
        for (Pattern pattern : P) {
            itensUnicosHS.addAll(pattern.getItens());
        }
        int[] itensUnicos = new int[itensUnicosHS.size()];
        
        //Salvando itens únicos num array
        Iterator<Integer> iterator = itensUnicosHS.iterator();
        int indice = 0;
        while(iterator.hasNext()){
            itensUnicos[indice++] = iterator.next();
        }
        
        //Contando quantas vezes cada item aparece na descrição das top-k DPs
        int[] frequenciaItensUnicos = new int[itensUnicos.length];
        for(int i = 0; i < itensUnicos.length; i++){
            for (Pattern pattern : P) {
                //se DP contém item único
                if (pattern.getItens().contains(itensUnicos[i])) {
                    frequenciaItensUnicos[i]++;
                }
            }
        }
                
        //Identificando frequência do item mais frequênte
        int maxFrequency = 0;
        for (int frequenciaItensUnico : frequenciaItensUnicos) {
            if (maxFrequency < frequenciaItensUnico) {
                maxFrequency = frequenciaItensUnico;
            }
        }

        return (double)maxFrequency/(double)P.length;
    }
    
    public static double metricaMedia(Pattern[] P, Base b, String tipoMetrica){
        if(P == null){
            return Double.NaN;
        }
        
        int i = 0;
        double total = 0.0;
        
        switch(tipoMetrica){
            case Const.METRICA_CONF:
                for(; i < P.length; i++){
                    total += DPinfo.conf(P[i]);
                }
                break;
            case Const.METRICA_COV:
                for(; i < P.length; i++){
                    total += DPinfo.cov(P[i], b);
                }
                break;
            case Const.METRICA_DIFF_SUP:
                for(; i < P.length; i++){
                    total += DPinfo.DiffSup(P[i], b);
                }
                break;
            case Const.METRICA_FP:
                for(; i < P.length; i++){
                    total += DPinfo.FP(P[i]);
                }
                break;
            case Const.METRICA_GROWTH_RATE:
                for(; i < P.length; i++){
                    total += DPinfo.GrowthRate(P[i], b);
                }
                break;
            case Const.METRICA_ODDS_RATIO:
                for(; i < P.length; i++){
                    total += DPinfo.OddsRatio(P[i], b);
                }
                break;
            case Const.METRICA_Qg:
                for(; i < P.length; i++){
                    total += DPinfo.Qg(P[i]);
                }
                break;
            case Const.METRICA_SIZE:
                for(; i < P.length; i++){
                    total += DPinfo.size(P[i]);
                }
                break;
            case Const.METRICA_SUPP:
                for(; i < P.length; i++){
                    total += DPinfo.supp(P[i], b);
                }
                break;
            case Const.METRICA_TP:
                for(; i < P.length; i++){
                    total += DPinfo.TP(P[i]);
                }
                break;
            case Const.METRICA_WRACC:
                for(; i < P.length; i++){
                    total += DPinfo.WRAcc(P[i], b);
                }
                break;
            case Const.METRICA_WRACC_NORMALIZED:
                for(; i < P.length; i++){
                    total += DPinfo.WRAccNormalized(P[i], b);
                }
                break;
            case Const.METRICA_SUPP_NEGATIVO:
                for(; i < P.length; i++){
                    total += DPinfo.suppNegativo(P[i], b);
                }
                break;
            case Const.METRICA_SUPP_POSITIVO:
                for(; i < P.length; i++){
                    total += DPinfo.suppPositivo(P[i], b);
                }
                break;
            case Const.METRICA_OVERALL_SUPP_POSITIVO:
                //Nesse caso o cálculo é diferente. Eu quero a união dos exemplos cobertos pelo conjunto de regras.
                return DPinfo.overallSuppPositive(P, b);
            case Const.METRICA_COVER_REDUNDANCY_POSITIVO:
                //Nesse caso o cálculo é diferente. Eu quero a união dos exemplos cobertos pelo conjunto de regras.
                return DPinfo.coverRedundancy(P, b);
            case Const.METRICA_DESCRIPTION_REDUNDANCY_DENSITY:
                //Nesse caso o cálculo é diferente. Eu quero a união dos exemplos cobertos pelo conjunto de regras.
                return DPinfo.descritionRedundancyDensity(P);
            case Const.METRICA_DESCRIPTION_REDUNDANCY_DOMINATOR:
                //Nesse caso o cálculo é diferente. Eu quero a união dos exemplos cobertos pelo conjunto de regras.
                return DPinfo.descritionRedundancyDominator(P);
            case Const.METRICA_K:
                //Nesse caso o cálculo é diferente. Eu quero a união dos exemplos cobertos pelo conjunto de regras.
                return P.length;            
            case Const.METRICA_CHI_QUAD:
                for(; i < P.length; i++){
                    total += DPinfo.chi_quad(P[i], b);
                }
                break;
            case Const.METRICA_P_VALUE:
                for(; i < P.length; i++){
                    total += DPinfo.p_value(P[i], b);
                }
                break;
            case Const.METRICA_LIFT:
                for(; i < P.length; i++){
                    total += DPinfo.lift(P[i], b);
                }
                break;
        }

        return total/(double)i;
    }
    
    public static double metricaMedia(Resultado[] R, Base b, String tipoMetrica){
        int i = 0;
        double total = 0.0;
                
        if(tipoMetrica.equals(Const.METRICA_TIME)){
            for(; i < R.length; i++){
                total += R[i].getTempoExecucao();
            }
        }else if(tipoMetrica.equals(Const.METRICA_NUMERO_TESTES)){
            for(; i < R.length; i++){
                total += R[i].getNumeroTestes();
            }
        }else{
            for(; i < R.length; i++){
                total += DPinfo.metricaMedia(R[i].getDPs(), b, tipoMetrica);
            }
        }

        return total/(double)i;
    }
    
//    public static double metricaMedia(Simulacao[] simulacoes, String tipoMetrica, BasesArrayList bases){
//        int i = 0;
//        double total = 0.0;
//        for(; i < simulacoes.length; i++){
//            Resultado[] resultados = simulacoes[i].getResultados();
//            Base base = bases.getBase( simulacoes[i].getNomeBase() );
//            total += DPinfo.metricaMedia(resultados, base, tipoMetrica);
//        }        
//                        
//        double media = total/(double)i;
//        
//        return media;
//    }

    //Retorna o número de vezes que cada Pattern de Pdistintos aparece em P.
    public static int[] frequenciaPatterns(ArrayList<Pattern> P, Pattern[] Pdistintos){
        int[] frequencia = new int[Pdistintos.length];
        
        for(int i = 0; i < Pdistintos.length; i++){
            for (Pattern pattern : P) {
                if (DPinfo.isEqual(Pdistintos[i], pattern)) {
                    frequencia[i]++;
                }
            }
        }
        
        return frequencia;
    }

    public static Pattern[] patternDistintos(ArrayList<Pattern> P){
        ArrayList<Pattern> pDistintosAux = new ArrayList<>();
        pDistintosAux.add(P.getFirst());
        for(int i = 1; i < P.size(); i++){
            if(DPinfo.isDistinto(pDistintosAux, P.get(i))){
                pDistintosAux.add(P.get(i));
            }
        }
        
        
        Pattern[] pDistintosArray = new Pattern[pDistintosAux.size()];
        for(int i = 0; i < pDistintosArray.length; i++){
            pDistintosArray[i] = pDistintosAux.get(i);
        }        
        
        return pDistintosArray;        
    }
    
    private static boolean isEqual(Pattern p1, Pattern p2){
        HashSet<Integer> itens1 = p1.getItens();
        HashSet<Integer> itens2 = p2.getItens();        
        return ( itens1.containsAll(itens2) && itens2.containsAll(itens1) );                
    }
    
    private static boolean isDistinto(ArrayList<Pattern> P, Pattern p){
        for (Pattern pattern : P) {
            if (DPinfo.isEqual(p, pattern)) {
                return false;
            }
        }
        return true;
    }
 
    private static int getPosicaoRanking(int itemOriginal, int[] rankingDP1){
        for(int i = 0; i < rankingDP1.length; i++){
            if(rankingDP1[i] == itemOriginal){
                return i;
            }
        }
        return -1;
    }
    
    public static void imprimirItens(Pattern p){
        HashSet<Integer> itens = p.getItens();
        Iterator<Integer> iterator = itens.iterator();
        System.out.print("(");
        while(iterator.hasNext()){
            System.out.print(iterator.next()+",");
        }
        System.out.print(")");
    }
}
