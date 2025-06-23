package com.bcopstein.sistvendas.aplicacao.relatorios;

import com.bcopstein.sistvendas.aplicacao.dtos.ClienteComprasDTO;
import java.util.List;

public interface RelatorioClientesMaisCompraram {
    String gerarRelatorioTexto(List<ClienteComprasDTO> clientes);
    // Futuro: String gerarRelatorioHTML(...), String gerarRelatorioXML(...)
} 