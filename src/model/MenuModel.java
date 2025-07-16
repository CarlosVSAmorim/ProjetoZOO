package model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects; // Importar Objects para validações

/**
 * Representa o modelo de dados para o menu principal da aplicação.
 * Armazena o título do menu e um mapa de opções, onde a chave é um identificador
 * único (String) e o valor é o texto a ser exibido no botão (String).
 *
 * Esta classe é projetada para ser imutável após a construção, garantindo
 * a consistência dos dados do menu.
 */
public final class MenuModel { // 'final' para imutabilidade
    private final String titulo; // 'final' para imutabilidade
    private final Map<String, String> opcoes; // 'final' para imutabilidade

    /**
     * Construtor padrão que inicializa o menu com um título e opções predefinidas.
     * As opções são: Gerenciar Animais, Gerenciar Usuários, Gerenciar Cuidadores,
     * Relatórios e Logout.
     */
    public MenuModel() {
        this("Menu Principal - Zoológico", createDefaultOptions());
    }

    /**
     * Construtor que permite inicializar o menu com um título personalizado
     * e opções predefinidas.
     *
     * @param titulo O título a ser exibido no menu. Não pode ser nulo ou vazio.
     * @throws IllegalArgumentException se o título for nulo ou vazio.
     */
    public MenuModel(String titulo) {
        this(titulo, createDefaultOptions());
    }

    /**
     * Construtor que permite inicializar o menu com um título e um mapa de opções personalizados.
     * O mapa de opções é copiado para garantir a imutabilidade.
     *
     * @param titulo O título a ser exibido no menu. Não pode ser nulo ou vazio.
     * @param opcoes Um mapa de identificadores para textos de opções. Não pode ser nulo.
     *               As chaves e valores do mapa não podem ser nulos ou vazios.
     * @throws IllegalArgumentException se o título for nulo/vazio ou se as opções forem nulas
     *                                  ou contiverem chaves/valores nulos/vazios.
     */
    public MenuModel(String titulo, Map<String, String> opcoes) {
        // Validação do título
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O título do menu não pode ser nulo ou vazio.");
        }
        this.titulo = titulo;

        // Validação e cópia das opções para garantir imutabilidade
        Objects.requireNonNull(opcoes, "O mapa de opções não pode ser nulo.");
        LinkedHashMap<String, String> tempOpcoes = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : opcoes.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key == null || key.trim().isEmpty()) {
                throw new IllegalArgumentException("A chave da opção não pode ser nula ou vazia.");
            }
            if (value == null || value.trim().isEmpty()) {
                throw new IllegalArgumentException("O texto da opção para a chave '" + key + "' não pode ser nulo ou vazio.");
            }
            tempOpcoes.put(key, value);
        }
        this.opcoes = Collections.unmodifiableMap(tempOpcoes); // Torna o mapa imutável
    }

    /**
     * Cria e retorna um mapa de opções padrão para o menu.
     *
     * @return Um mapa LinkedHashMap contendo as opções padrão.
     */
    private static Map<String, String> createDefaultOptions() {
        LinkedHashMap<String, String> defaultOptions = new LinkedHashMap<>();
        defaultOptions.put("animal", "Gerenciar Animais");
        defaultOptions.put("user", "Gerenciar Usuários");
        defaultOptions.put("cuidador", "Gerenciar Cuidadores");
        defaultOptions.put("report", "Relatórios");
        defaultOptions.put("logout", "Logout");
        defaultOptions.put("exit", "Sair da Aplicação"); // Adicionando uma opção de saída explícita
        return defaultOptions;
    }

    /**
     * Retorna o título do menu.
     *
     * @return O título do menu.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Retorna um mapa imutável das opções do menu.
     * Qualquer tentativa de modificar o mapa retornado resultará em uma
     * UnsupportedOperationException.
     *
     * @return Um mapa imutável das opções do menu.
     */
    public Map<String, String> getOpcoes() {
        return opcoes;
    }

    /**
     * Retorna o texto de uma opção específica do menu dado seu identificador (chave).
     *
     * @param chave O identificador da opção.
     * @return O texto da opção correspondente, ou null se a chave não for encontrada.
     */
    public String getOpcaoTexto(String chave) {
        return opcoes.get(chave);
    }

    // Métodos de modificação removidos para garantir imutabilidade:
    // public void setTitulo(String titulo) { ... }
    // public void adicionarOpcao(String chave, String texto) { ... }
    // public void removerOpcao(String chave) { ... }

    /**
     * Retorna uma representação em string do objeto MenuModel.
     *
     * @return Uma string contendo o título e as opções do menu.
     */
    @Override
    public String toString() {
        return "MenuModel{" +
                "titulo='" + titulo + '\'' +
                ", opcoes=" + opcoes +
                '}';
    }

    /**
     * Compara este objeto MenuModel com outro para igualdade.
     * Dois objetos MenuModel são considerados iguais se tiverem o mesmo título
     * e as mesmas opções (em termos de chaves e valores).
     *
     * @param o O objeto a ser comparado.
     * @return true se os objetos forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuModel menuModel = (MenuModel) o;
        return Objects.equals(titulo, menuModel.titulo) &&
                Objects.equals(opcoes, menuModel.opcoes);
    }

    /**
     * Retorna um valor de código hash para o objeto.
     *
     * @return Um valor de código hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(titulo, opcoes);
    }
}
