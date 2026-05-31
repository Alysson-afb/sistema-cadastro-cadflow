package model.services;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.classes.Acolhido;
import model.classes.Familiar;
import com.lowagie.text.pdf.draw.LineSeparator;

public class RelatorioService {

    private FamiliarService familiarService = new FamiliarService();
    
    
    private static final Font FONTE_TITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
    private static final Font FONTE_SUBTITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
    private static final Font FONTE_NEGRIITO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
    private static final Font FONTE_NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 10);

    public void gerarFichaAcolhido(Acolhido acolhido) {
        
        String nomeArquivo = "Ficha_" + acolhido.getNome().replace(" ", "_") + ".pdf";
        Document documento = new Document(PageSize.A4);

        try {
            PdfWriter.getInstance(documento, new FileOutputStream(nomeArquivo));
            documento.open();

            
            adicionarCabecalho(documento);

            
            adicionarTituloSecao(documento, "1. DADOS PESSOAIS");
            PdfPTable tabelaPessoal = criarTabelaDados();
            addDado(tabelaPessoal, "Nome Completo:", acolhido.getNome());
            addDado(tabelaPessoal, "Nome Social:", acolhido.getNomeSocial());
            addDado(tabelaPessoal, "CPF:", acolhido.getCpf());
            addDado(tabelaPessoal, "Data Nasc.:", formatarData(acolhido.getDataNascimento()));
            addDado(tabelaPessoal, "Sexo:", acolhido.getSexo());
            addDado(tabelaPessoal, "Naturalidade:", acolhido.getNaturalidade());
            addDado(tabelaPessoal, "Nacionalidade:", acolhido.getNacionalidade());
            documento.add(tabelaPessoal);

            
            adicionarTituloSecao(documento, "2. DADOS DO ACOLHIMENTO");
            PdfPTable tabelaAcolhimento = criarTabelaDados();
            addDado(tabelaAcolhimento, "Data Entrada:", formatarData(acolhido.getDataEntrada()));
            addDado(tabelaAcolhimento, "Responsável:", acolhido.getResponsavelAcolhimento());
            addDado(tabelaAcolhimento, "Motivo:", acolhido.getMotivoAcolhimento());
            documento.add(tabelaAcolhimento);
            
            
            if (acolhido.getDetalhesAcolhimento() != null && !acolhido.getDetalhesAcolhimento().isEmpty()) {
                Paragraph p = new Paragraph("Detalhes: " + acolhido.getDetalhesAcolhimento(), FONTE_NORMAL);
                p.setSpacingBefore(5);
                documento.add(p);
            }

            
            adicionarTituloSecao(documento, "3. PRONTUÁRIO TÉCNICO");
            documento.add(new Paragraph("Informações de Saúde:", FONTE_NEGRIITO));
            documento.add(new Paragraph(tratarTexto(acolhido.getInfoSaude()), FONTE_NORMAL));
            
            documento.add(new Paragraph("Histórico de Rua:", FONTE_NEGRIITO));
            documento.add(new Paragraph(tratarTexto(acolhido.getHistoricoRua()), FONTE_NORMAL));
            
            documento.add(new Paragraph("Avaliação Interdisciplinar:", FONTE_NEGRIITO));
            documento.add(new Paragraph(tratarTexto(acolhido.getAvaliacaoInterdisciplinar()), FONTE_NORMAL));

            
            adicionarTituloSecao(documento, "4. VÍNCULOS FAMILIARES");
            List<Familiar> familiares = familiarService.listarPorAcolhido(acolhido.getIdPessoa());
            
            if (familiares.isEmpty()) {
                documento.add(new Paragraph("Nenhum vínculo familiar registrado.", FONTE_NORMAL));
            } else {
                PdfPTable tabFam = new PdfPTable(3); 
                tabFam.setWidthPercentage(100);
                tabFam.setSpacingBefore(5);
                
                
                tabFam.addCell(criarCelulaHeader("Nome do Parente"));
                tabFam.addCell(criarCelulaHeader("Vínculo"));
                tabFam.addCell(criarCelulaHeader("Ocupação"));
                
                
                for (Familiar f : familiares) {
                    tabFam.addCell(new Phrase(f.getNomeParente(), FONTE_NORMAL));
                    tabFam.addCell(new Phrase(f.getParentesco(), FONTE_NORMAL));
                    tabFam.addCell(new Phrase(f.getOcupacao(), FONTE_NORMAL));
                }
                documento.add(tabFam);
            }

            
            
            if (acolhido.getObservacoes() != null && !acolhido.getObservacoes().trim().isEmpty()) {
                adicionarTituloSecao(documento, "5. OBSERVAÇÕES GERAIS");
                
                Paragraph pObs = new Paragraph(tratarTexto(acolhido.getObservacoes()), FONTE_NORMAL);
                pObs.setSpacingBefore(5);
                documento.add(pObs);
            }
            documento.close();

            
            File arquivo = new File(nomeArquivo);
            if (arquivo.exists()) {
                Desktop.getDesktop().open(arquivo);
            }

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    

    private void adicionarCabecalho(Document doc) throws DocumentException {
        Paragraph pTitulo = new Paragraph("CadFlow - Sistema de Gestão de Acolhimento", FONTE_TITULO);
        pTitulo.setAlignment(Element.ALIGN_CENTER);
        doc.add(pTitulo);
        
        Paragraph pSub = new Paragraph("Ficha Cadastral Individual", FONTE_SUBTITULO);
        pSub.setAlignment(Element.ALIGN_CENTER);
        pSub.setSpacingAfter(20);
        doc.add(pSub);
    }

    private void adicionarTituloSecao(Document doc, String texto) throws DocumentException {
        
        Paragraph p = new Paragraph(texto, FONTE_SUBTITULO);
        p.setSpacingBefore(15);
        p.setSpacingAfter(2); 
        doc.add(p);

        
        LineSeparator linha = new LineSeparator();
        linha.setLineWidth(1f); 
        linha.setPercentage(100); 
        doc.add(linha);
        
        
        doc.add(new Paragraph(" ")); 
    }

    private PdfPTable criarTabelaDados() {
        PdfPTable table = new PdfPTable(2); 
        table.setWidthPercentage(100);
        try {
            table.setWidths(new float[]{30f, 70f}); 
        } catch (DocumentException e) {}
        return table;
    }

    private void addDado(PdfPTable table, String label, String valor) {
        PdfPCell cellLabel = new PdfPCell(new Phrase(label, FONTE_NEGRIITO));
        cellLabel.setBorder(0); 
        
        PdfPCell cellValor = new PdfPCell(new Phrase(tratarTexto(valor), FONTE_NORMAL));
        cellValor.setBorder(0);
        
        table.addCell(cellLabel);
        table.addCell(cellValor);
    }
    
    private PdfPCell criarCelulaHeader(String texto) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, FONTE_NEGRIITO));
        cell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
        return cell;
    }

    private String tratarTexto(String texto) {
        return (texto == null || texto.isEmpty()) ? "---" : texto;
    }
    
    private String formatarData(java.time.LocalDate data) {
        if (data == null) return "---";
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}