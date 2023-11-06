package digytal.desktop.app.form.modulo.cadastro.produto.unidademedida;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import digytal.desktop.app.context.Context;
import digytal.desktop.app.model.modulo.cadastro.produto.unidademedida.UnidadeMedidaResponse;
import digytal.desktop.app.service.modulo.cadastro.produto.UnidadeMedidaService;
import digytal.desktop.components.desktop.FormularioConsulta;
import digytal.desktop.components.desktop.ss.SSCampoTexto;
import digytal.desktop.components.desktop.ss.SSMensagem;
import digytal.desktop.components.desktop.ss.SSPosicaoRotulo;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmUnidadeMedidaConsulta extends FormularioConsulta {
	
	@Autowired
	private UnidadeMedidaService service;
	private SSCampoTexto cNome = new SSCampoTexto();
	public FrmUnidadeMedidaConsulta() {
		setTitulo("Unidades de Medida");
		setDescricao("Listagem das Unidades de Medida dos Produtos");
		
		JPanel filtros = new JPanel(new GridBagLayout());
		GridBagConstraints gbc_cNome = new GridBagConstraints();
		gbc_cNome.weightx = 1.0;
		gbc_cNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_cNome.insets = new Insets(5, 5, 5, 0);
		gbc_cNome.anchor = GridBagConstraints.NORTHWEST;
		gbc_cNome.gridx = 0;
		gbc_cNome.gridy = 0;
		filtros.add(cNome, gbc_cNome);
		
		
		
		filtros.add(bBuscar, getGbcBuscar(1, 0));
		
		getConteudo().add(filtros,BorderLayout.NORTH);
		
		getTabela().adicionarColuna(0, "ID", "id");
		getTabela().adicionarColuna(1, "Nome", "nome");
		getTabela().adicionarColuna(2, "Sigla", "sigla");
		getTabela().adicionarColuna(3, "Embalagem", "embalagem");
		
		getTabela().definirLarguraColunas(30, 100,70,70);
		
		bIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				incluir(Context.getBean(FrmUndideMedidaCadastro.class));
			}
		});
		
		bAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				alterar(Context.getBean(FrmUndideMedidaCadastro.class));
			}
		});
		//setScrollSize(600, 400);
		definir();
	}
	private void definir() {
		cNome.setRotulo("Nome");
		cNome.setRotuloPosicao(SSPosicaoRotulo.ESQUERDA);
		cNome.setColunas(15);
	}
	public void filtrar() {
		try {
			String nome = cNome.getText();
			if (nome.isEmpty()) {
				SSMensagem.avisa("Informe um nome da Unidade de Medida para a pesquisa");
				return;
			}
			List<UnidadeMedidaResponse> registros = service.consultar(nome);
			exibirRegistros(registros);
		} catch (Exception e) {
			e.printStackTrace();
			SSMensagem.erro(e.getMessage());
		}
	}

}