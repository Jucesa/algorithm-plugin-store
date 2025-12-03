/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd;

import dp.*;
import evolucionario.INICIALIZAR;
import evolucionario.SELECAO;
import simulacoes.DPinfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

/**
 *
 * @author Marianna
 */
public class SD {
    public Pattern[] run(double min_support, int beam_width, String tipoAvaliacao, int k, double maxTimeSegundos) throws IOException {
        long t0 = System.currentTimeMillis(); //Initial time
        Pattern[] beam = new Pattern[beam_width];
        Pattern[] newBeam = new Pattern[beam_width];
        Pattern[] Pk = new Pattern[k];
        for(int i = 0; i < beam_width; i++){
            beam[i] = new Pattern(new HashSet<Integer>(), tipoAvaliacao);
            newBeam[i] = new Pattern(new HashSet<Integer>(), tipoAvaliacao);
        }
        Pattern[] I = INICIALIZAR.D1(tipoAvaliacao);
        Arrays.sort(I);
        boolean houveMelhoria = true;
        int ciclo = 0;
        while(houveMelhoria){
            //System.out.println("\nCiclo: " + ciclo++);
            double qualidadePiorAntes = beam[beam_width-1].getQualidade();
            for(int i = 0; i < beam_width; i++){
                
                //Finalização por tempo.
                double tempo = (System.currentTimeMillis() - t0)/1000.0; //time
                if(maxTimeSegundos > 0 && tempo > maxTimeSegundos){
                    System.arraycopy(newBeam, 0, Pk, 0, Pk.length);
                    return Pk;
                }

                for(int j = 0; j < D.numeroItensUtilizados; j++){
                    
                    HashSet<Integer> itens = (HashSet<Integer>)beam[i].getItens().clone();
                    itens.add(D.itensUtilizados[j]);
                    Pattern p = new Pattern(itens, tipoAvaliacao);
                    double suporte = (double)p.getTP()/(double)D.numeroExemplos;
                    boolean ehRelevante = SELECAO.ehRelevante(p, newBeam);
                    double qualidade = p.getQualidade();
                    //Se valor de qualidade é menor que zero ou menor que a qualidade do pior Pattern
                    //ele não será incluido no newBean
                    if(qualidade > newBeam[beam_width-1].getQualidade() &&
                       suporte >= min_support &&
                       ehRelevante){
                            newBeam[beam_width-1] = p;
                            Arrays.sort(newBeam);                
                    }                    
                }                
            }
            if(newBeam[beam_width-1].getQualidade() == qualidadePiorAntes){
                houveMelhoria = false;
            }
            beam = newBeam.clone();
        }        

        return Pk;
    }
}
