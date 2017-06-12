package br.com.gamesseller.yugiooh;

/**
 * Created by 16165846 on 21/03/2017.
 */

public class Carta {
    private String nome;
    private String descricao;
    private int idCarta;
    private int elemento;
    private int nivel;
    private int imagem;
    private int imagemZoom;
    private int tipo;
    private int atk;
    private int def;

    public Carta (String nome, String descricao, int elemento, int nivel, int imagem, int tipo, int atk, int def, int imagemZoom, int idCarta){
        setNome(nome);
        setDescricao(descricao);
        setElemento(elemento);
        setNivel(nivel);
        setImagem(imagem);
        setTipo(tipo);
        setAtk(atk);
        setDef(def);
        setImagemZoom(imagemZoom);
        setIdCarta(idCarta);
    }

    public Carta (int imagem){
        setImagem(imagem);
        setElemento(elemento);
        setTipo(tipo);
    }

    public int getIdCarta() {
        return idCarta;
    }

    public void setIdCarta(int idCarta) {
        this.idCarta = idCarta;
    }

    public int getImagemZoom() {
        return imagemZoom;
    }

    public void setImagemZoom(int imagemZoom) {
        this.imagemZoom = imagemZoom;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getElemento() {
        return elemento;
    }

    public void setElemento(int elemento) {
        this.elemento = elemento;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }
}
