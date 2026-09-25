package br.com.projetosigse.service;

import br.com.projetosigse.dto.CartaoDtos.CartaoRequest;
import br.com.projetosigse.dto.CartaoDtos.CartaoResponse;
import br.com.projetosigse.exception.RecursoNaoEncontradoException;
import br.com.projetosigse.exception.RegraNegocioException;
import br.com.projetosigse.model.CartaoRfid;
import br.com.projetosigse.model.TipoCartao;
import br.com.projetosigse.repository.CartaoRfidRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartaoService {

    private final CartaoRfidRepository cartaoRfidRepository;
    private final AlunoService alunoService;
    private final ResponsavelService responsavelService;

    public CartaoService(
            CartaoRfidRepository cartaoRfidRepository,
            AlunoService alunoService,
            ResponsavelService responsavelService
    ) {
        this.cartaoRfidRepository = cartaoRfidRepository;
        this.alunoService = alunoService;
        this.responsavelService = responsavelService;
    }

    @Transactional
    public CartaoResponse criar(CartaoRequest request) {
        cartaoRfidRepository.findByUidIgnoreCase(request.uid().trim()).ifPresent(c -> {
            throw new RegraNegocioException("UID já cadastrado.");
        });
        CartaoRfid cartao = new CartaoRfid();
        aplicar(cartao, request);
        return CartaoResponse.from(cartaoRfidRepository.save(cartao));
    }

    @Transactional(readOnly = true)
    public List<CartaoResponse> listar() {
        return cartaoRfidRepository.findAll().stream().map(CartaoResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public CartaoResponse buscar(Long id) {
        return CartaoResponse.from(obter(id));
    }

    @Transactional
    public CartaoResponse atualizar(Long id, CartaoRequest request) {
        CartaoRfid cartao = obter(id);
        aplicar(cartao, request);
        return CartaoResponse.from(cartaoRfidRepository.save(cartao));
    }

    public CartaoRfid obterPorUid(String uid) {
        return cartaoRfidRepository.findByUidIgnoreCase(uid.trim())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cartão RFID não encontrado: " + uid));
    }

    private CartaoRfid obter(Long id) {
        return cartaoRfidRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cartão não encontrado: " + id));
    }

    private void aplicar(CartaoRfid cartao, CartaoRequest request) {
        cartao.setUid(request.uid().trim().toUpperCase());
        cartao.setTipo(request.tipo());
        cartao.setAtivo(request.ativo() == null || request.ativo());
        if (request.tipo() == TipoCartao.ALUNO) {
            if (request.alunoId() == null) {
                throw new RegraNegocioException("Cartão de aluno precisa de alunoId.");
            }
            cartao.setAluno(alunoService.obter(request.alunoId()));
            cartao.setResponsavel(null);
        } else {
            if (request.responsavelId() == null) {
                throw new RegraNegocioException("Cartão de responsável precisa de responsavelId.");
            }
            cartao.setResponsavel(responsavelService.obter(request.responsavelId()));
            cartao.setAluno(null);
        }
    }
}
