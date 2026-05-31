
package model.classes;


public class MotivoAcolhimento {
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public MotivoAcolhimento(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "MotivoAcolhimento{" + "descricao=" + descricao + '}';
    }
}
