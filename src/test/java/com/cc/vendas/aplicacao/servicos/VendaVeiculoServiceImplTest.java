package com.cc.vendas.aplicacao.servicos;

import com.cc.vendas.aplicacao.dto.saida.VeiculoResumoOutput;
import com.cc.vendas.dominio.excecao.RegraNegocioException;
import com.cc.vendas.dominio.veiculo.StatusVeiculo;
import com.cc.vendas.dominio.veiculo.Veiculo;
import com.cc.vendas.dominio.veiculo.VeiculoRepository;
import com.cc.vendas.dominio.venda.VendaVeiculo;
import com.cc.vendas.dominio.venda.VendaVeiculoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendaVeiculoServiceImplTest {
    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private VendaVeiculoRepository vendaRepository;

    @InjectMocks
    private VendaVeiculoServiceImpl service;

    private final UUID veiculoID = UUID.randomUUID();
    private final String doc = "12345678910";

    @Test
    void deveRegistrarVenda() {
        Veiculo veiculo = Veiculo.reconstituir(
                veiculoID,
                "Toyota",
                "Corolla",
                "Branco",
                2022,
                BigDecimal.valueOf(120000),
                StatusVeiculo.DISPONIVEL_PARA_VENDA,
                null,
                null
        );

        when(veiculoRepository.buscarPorId(veiculoID))
                .thenReturn(Optional.of(veiculo));

        VeiculoResumoOutput output = service.registrarVenda(veiculoID, doc);

        assertNotNull(output);

        assertEquals(veiculo.getId(), output.id());
        assertEquals(StatusVeiculo.VENDIDO, output.statusVeiculo());
        assertNotNull(output.dataVenda());

        verify(veiculoRepository).buscarPorId(veiculoID);
        verify(veiculoRepository).salvar(veiculo);

        verify(vendaRepository).salvar(any(VendaVeiculo.class));

        verifyNoMoreInteractions(veiculoRepository, vendaRepository);
    }

    @Test
    void deveLancarExcecaoQuandoVeiculoNaoEncontrado() {

        when(veiculoRepository.buscarPorId(veiculoID))
                .thenReturn(Optional.empty());

        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> service.registrarVenda(veiculoID, doc)
        );

        assertEquals("Veículo não encontrado", exception.getMessage());

        verify(veiculoRepository).buscarPorId(veiculoID);

        verifyNoInteractions(vendaRepository);
        verify(veiculoRepository, never()).salvar(any(Veiculo.class));
        verify(vendaRepository, never()).salvar(any(VendaVeiculo.class));

        verifyNoMoreInteractions(veiculoRepository);
    }

    @Test
    void naoDeveSalvarVendaQuandoRegistrarVendaLancarExcecao() {

        Veiculo veiculo = spy(Veiculo.criar(
                "Toyota",
                "Corolla",
                "Branco",
                2022,
                BigDecimal.valueOf(120000.0)
        ));

        doThrow(new RegraNegocioException("Veículo já vendido"))
                .when(veiculo)
                .registrarVenda(doc);

        when(veiculoRepository.buscarPorId(veiculoID))
                .thenReturn(Optional.of(veiculo));

        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> service.registrarVenda(veiculoID, doc)
        );

        assertEquals("Veículo já vendido", exception.getMessage());

        verify(veiculoRepository).buscarPorId(veiculoID);
        verify(veiculo, times(1)).registrarVenda(doc);

        verify(veiculoRepository, never()).salvar(any(Veiculo.class));
        verify(vendaRepository, never()).salvar(any(VendaVeiculo.class));

        verifyNoMoreInteractions(veiculoRepository, vendaRepository);
    }
}
