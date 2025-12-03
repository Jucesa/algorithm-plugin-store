package com.mynedata.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

/**
 * Objeto de Transferência de Dados (DTO) que padroniza a resposta
 * de qualquer algoritmo de mineração para o Backend e Frontend.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisResult {

    // Nome do algoritmo que gerou este resultado (ex: "K-Means", "SD Evolucionário")
    private String algorithmUsed;

    // Tempo total de execução em milissegundos
    private long processingTimeMs;

    // Métrica de qualidade (ex: Acurácia, Qualidade Média, Silhouette Score)
    // Se não aplicável, pode ser 0.0
    private double accuracy;

    // Texto descritivo para exibição rápida (ex: "Foram encontrados 5 clusters.")
    private String summary;

    // Mapa flexível para dados complexos (ex: pontos para gráficos, lista de regras, matrizes)
    // O Frontend deve saber ler as chaves específicas que cada algoritmo manda aqui.
    private Map<String, Object> details;
}