 package com.algaworks.algafood.domain.service;

 import com.algaworks.algafood.domain.exception.NegocioException;
 import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
 import com.algaworks.algafood.domain.model.Grupo;
 import com.algaworks.algafood.domain.model.Usuario;
 import com.algaworks.algafood.domain.repository.UsuarioRepository;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

 import javax.persistence.EntityManager;
 import javax.transaction.Transactional;
 import java.util.Optional;

 @Service
 public class UsuarioService {

     @Autowired
     private UsuarioRepository usuarioRepository;

     @Autowired
     private EntityManager entityManager;

     @Autowired
     private GrupoService grupoService;

     @Transactional
     public Usuario salvar(Usuario usuario) {
         entityManager.detach(usuario);

         Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
            throw new NegocioException(String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
        }

        return usuarioRepository.save(usuario);
     }

     @Transactional
     public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
         Usuario usuario = buscar(usuarioId);

         if (usuario.senhaNaoCoincideCom(senhaAtual)) {
             throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
         }

         usuario.setSenha(novaSenha);
     }

     @Transactional
     public void desassociarGrupo(Long usuarioId, Long grupoId) {
         Usuario usuario = buscar(usuarioId);
         Grupo grupo = grupoService.buscarOuFalhar(grupoId);

         usuario.removerGrupo(grupo);
     }

     @Transactional
     public void associarGrupo(Long usuarioId, Long grupoId) {
         Usuario usuario = buscar(usuarioId);
         Grupo grupo = grupoService.buscarOuFalhar(grupoId);

         usuario.adicionarGrupo(grupo);
     }

     public Usuario buscar(Long usuarioId) {
         return usuarioRepository.findById(usuarioId)
             .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
     }
 }