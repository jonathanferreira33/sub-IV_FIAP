package com.cc.vendas.aplicacao.servicos;

import com.cc.vendas.aplicacao.dto.entrada.AtualizarVeiculoInput;
import com.cc.vendas.aplicacao.dto.entrada.RegistrarVeiculoInput;
import com.cc.vendas.aplicacao.dto.saida.VeiculoResumoOutput;
import com.cc.vendas.dominio.excecao.RegraNegocioException;
import com.cc.vendas.dominio.veiculo.StatusVeiculo;
import com.cc.vendas.dominio.veiculo.Veiculo;
import com.cc.vendas.dominio.veiculo.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeiculoServiceImplTest {

    @Mock
    private VeiculoRepository repository;

    @InjectMocks
    private VeiculoServiceImpl service;

    private final UUID veiculoID = UUID.randomUUID();
    private final String doc = "12345678910";

    @Test
    void deveAtualizarVeiculo() {


        Veiculo veiculo = Veiculo.reconstituir(
                veiculoID,
                "Ford",
                "Ka",
                "Prata",
                2020,
                BigDecimal.valueOf(45000.0),
                StatusVeiculo.DISPONIVEL_PARA_VENDA,
                doc,
                Instant.now()
        );

        AtualizarVeiculoInput input = new AtualizarVeiculoInput(
                "Chevrolet",
                "Onix",
                "Branco",
                2022,
                BigDecimal.valueOf(65000.0)
        );

        when(repository.buscarPorId(veiculoID))
                .thenReturn(Optional.of(veiculo));

        VeiculoResumoOutput output = service.atualizarDadosVeiculo(veiculoID, input);

        verify(repository).buscarPorId(veiculoID);
        verify(repository).salvar(veiculo);
        verifyNoMoreInteractions(repository);

        assertEquals("Chevrolet".toUpperCase(Locale.ROOT), veiculo.getMarca().toString());
        assertEquals("Onix", veiculo.getModelo());
        assertEquals("Branco".toUpperCase(Locale.ROOT), veiculo.getCor().toString());
        assertEquals(2022, veiculo.getAno());
        assertEquals(BigDecimal.valueOf(65000.0), veiculo.getPreco());

        assertNotNull(output);
    }

     @Test
     void deveLancarExcecaoAoAtualizarQuandoNaoEncontrado() {
         UUID id = UUID.randomUUID();

         AtualizarVeiculoInput input = mock(AtualizarVeiculoInput.class);

         when(repository.buscarPorId(id))
                 .thenReturn(Optional.empty());

         EntityNotFoundException exception = assertThrows(
                 EntityNotFoundException.class,
                 () -> service.atualizarDadosVeiculo(id, input)
         );

         assertEquals("Veículo não encontrado", exception.getMessage());

         verify(repository).buscarPorId(id);
         verify(repository, never()).salvar(any(Veiculo.class));
         verifyNoMoreInteractions(repository);

     }

    @Test
    void deveBuscarVeiculosDisponiveis() {
        Veiculo veiculo1 = Veiculo.criar(
                "Ford",
                "Ka",
                "Prata",
                2020,
                BigDecimal.valueOf(45000.0)
        );
        veiculo1.alterarStatusParaDisponivel();

        Veiculo veiculo2 = Veiculo.criar(
                "Chevrolet",
                "Onix",
                "Branco",
                2022,
                BigDecimal.valueOf(65000.0)
        );
        veiculo2.alterarStatusParaDisponivel();

        when(repository.buscarTodosVeiculosPorStatusOrdenadoPorPreco(
                StatusVeiculo.DISPONIVEL_PARA_VENDA.name()))
                .thenReturn(List.of(veiculo1, veiculo2));

        List<VeiculoResumoOutput> resultado = service.buscarVeiculosDisponiveis();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        verify(repository)
                .buscarTodosVeiculosPorStatusOrdenadoPorPreco(
                        StatusVeiculo.DISPONIVEL_PARA_VENDA.name());

        verifyNoMoreInteractions(repository);
    }

    @Test
    void deveRetornarListaVaziaAoBuscarDisponiveis() {
        when(repository.buscarTodosVeiculosPorStatusOrdenadoPorPreco(
                StatusVeiculo.DISPONIVEL_PARA_VENDA.name()))
                .thenReturn(Collections.emptyList());

        List<VeiculoResumoOutput> resultado = service.buscarVeiculosDisponiveis();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(repository)
                .buscarTodosVeiculosPorStatusOrdenadoPorPreco(
                        StatusVeiculo.DISPONIVEL_PARA_VENDA.name());

        verifyNoMoreInteractions(repository);
    }

    @Test
    void deveBuscarVeiculosVendidos() {
        Veiculo veiculo1 = Veiculo.criar(
                "Honda",
                "Civic",
                "Preto",
                2021,
                BigDecimal.valueOf(95000.0)
        );

        Veiculo veiculo2 = Veiculo.criar(
                "Toyota",
                "Corolla",
                "Branco",
                2023,
                BigDecimal.valueOf(120000.0)
        );

        when(repository.buscarTodosVeiculosPorStatusOrdenadoPorPreco(
                StatusVeiculo.VENDIDO.name()))
                .thenReturn(List.of(veiculo1, veiculo2));

        List<VeiculoResumoOutput> resultado = service.buscarVeiculosVendidos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        verify(repository).buscarTodosVeiculosPorStatusOrdenadoPorPreco(
                StatusVeiculo.VENDIDO.name());

        verifyNoMoreInteractions(repository);
    }

    @Test
    void deveRetornarListaVaziaAoBuscarVendidos() {
        when(repository.buscarTodosVeiculosPorStatusOrdenadoPorPreco(
                StatusVeiculo.VENDIDO.name()))
                .thenReturn(Collections.emptyList());

        List<VeiculoResumoOutput> resultado = service.buscarVeiculosVendidos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(repository)
                .buscarTodosVeiculosPorStatusOrdenadoPorPreco(
                        StatusVeiculo.VENDIDO.name());

        verifyNoMoreInteractions(repository);
    }

    @Test
    void deveBuscarVeiculoPorId() {

        Veiculo veiculo = Veiculo.reconstituir(
                veiculoID,
                "Ford",
                "Ka",
                "Prata",
                2020,
                BigDecimal.valueOf(45000.0),
                StatusVeiculo.DISPONIVEL_PARA_VENDA,
                doc,
                Instant.now()
        );

        when(repository.buscarPorId(veiculoID))
                .thenReturn(Optional.of(veiculo));

        VeiculoResumoOutput resultado = service.buscarVeiculoPorId(veiculoID);

        assertNotNull(resultado);

        verify(repository).buscarPorId(veiculoID);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deveLancarExcecaoAoBuscarPorId() {
        UUID id = UUID.randomUUID();

        when(repository.buscarPorId(id))
                .thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> service.buscarVeiculoPorId(id)
        );

        assertEquals("Veiculo não encontrado", exception.getMessage());

        verify(repository).buscarPorId(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deveCadastrarVeiculo() {
        // Arrange
        RegistrarVeiculoInput input = new RegistrarVeiculoInput(
                "Toyota",
                "Corolla",
                "Branco",
                LocalDate.now().getYear(),
                BigDecimal.valueOf(125000.0)
        );

        ArgumentCaptor<Veiculo> captor =
                ArgumentCaptor.forClass(Veiculo.class);

        VeiculoResumoOutput output = service.cadastrarVeiculo(input);

        verify(repository).salvar(captor.capture());
        verifyNoMoreInteractions(repository);

        Veiculo veiculo = captor.getValue();

        assertNotNull(veiculo.getId());

        assertEquals("Toyota".toUpperCase(Locale.ROOT), veiculo.getMarca().toString());
        assertEquals("Corolla", veiculo.getModelo());
        assertEquals("Branco".toUpperCase(Locale.ROOT), veiculo.getCor().toString());
        assertEquals(LocalDate.now().getYear(), veiculo.getAno());
        assertEquals(BigDecimal.valueOf(125000.0), veiculo.getPreco());

        assertEquals(
                StatusVeiculo.DISPONIVEL_PARA_VENDA,
                veiculo.getStatus()
        );

        assertNotNull(output);
    }

    @Test
    void deveLancarExcecaoQuandoAnoMenorQue1886() {
        RegistrarVeiculoInput input = new RegistrarVeiculoInput(
                "Ford",
                "Modelo T",
                "Preto",
                1885,
                BigDecimal.valueOf(50000.0)
        );

        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> service.cadastrarVeiculo(input)
        );

        assertEquals("Ano inválido", exception.getMessage());

        verify(repository, never()).salvar(any(Veiculo.class));
        verifyNoInteractions(repository);
    }

    @Test
    void deveLancarExcecaoQuandoAnoMaiorQueAnoAtual() {

        int anoInvalido = LocalDate.now().getYear() + 1;

        RegistrarVeiculoInput input = new RegistrarVeiculoInput(
                "Toyota",
                "Corolla",
                "Branco",
                anoInvalido,
                BigDecimal.valueOf(120000.0)
        );

        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> service.cadastrarVeiculo(input)
        );

        assertEquals("Ano inválido", exception.getMessage());

        verifyNoInteractions(repository);
    }

        @Test
        void deveCadastrarVeiculoComAno1886() {
            RegistrarVeiculoInput input = new RegistrarVeiculoInput(
                    "Mercedes-Benz",
                    "Patent-Motorwagen",
                    "Preto",
                    1886,
                    BigDecimal.valueOf(1000000.0)
            );

            ArgumentCaptor<Veiculo> captor =
                    ArgumentCaptor.forClass(Veiculo.class);

            VeiculoResumoOutput output = service.cadastrarVeiculo(input);

            verify(repository).salvar(captor.capture());
            verifyNoMoreInteractions(repository);

            Veiculo veiculo = captor.getValue();

            assertEquals(1886, veiculo.getAno());
            assertEquals(StatusVeiculo.DISPONIVEL_PARA_VENDA, veiculo.getStatus());

            assertNotNull(output);
        }

    @Test
    void deveCadastrarVeiculoComAnoAtual() {
        int anoAtual = LocalDate.now().getYear();

        RegistrarVeiculoInput input = new RegistrarVeiculoInput(
                "Honda",
                "Civic",
                "Cinza",
                anoAtual,
                BigDecimal.valueOf(150000.0)
        );

        ArgumentCaptor<Veiculo> captor =
                ArgumentCaptor.forClass(Veiculo.class);

        VeiculoResumoOutput output = service.cadastrarVeiculo(input);

        verify(repository).salvar(captor.capture());
        verifyNoMoreInteractions(repository);

        Veiculo veiculo = captor.getValue();

        assertEquals(anoAtual, veiculo.getAno());
        assertEquals(StatusVeiculo.DISPONIVEL_PARA_VENDA, veiculo.getStatus());

        assertNotNull(output);
    }
}
