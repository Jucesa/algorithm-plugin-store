package com.mynedata.contract;

import java.io.File;
import java.util.Map;

/**
 * Interface padrão que os algoritmos de mineração devem implementar
 * para ser compatível com o sistema MyneData.
 */
public interface MiningAlgorithm {

    /**
     * ID único do algoritmo.
     * Deve ser uma string simples, sem espaços (ex: "kmeans", "random-forest").
     * Este ID é usado para ligar a opção do Frontend com a classe Java.
     */
    String getId();

    /**
     * Nome legível para humanos.
     * Exibido em logs e relatórios.
     */
    String getName();

    /**
     * O método principal de execução.
     * * @param csvFile O arquivo de dados bruto enviado pelo usuário.
     * @param params  Parâmetros de configuração opcionais vindos do Frontend (ex: {"k": "5", "target": "classe"}).
     * @return Um objeto AnalysisResult contendo os resultados padronizados.
     * @throws Exception Se ocorrer qualquer erro durante o processamento.
     */
    AnalysisResult execute(File csvFile, Map<String, String> params) throws Exception;
}