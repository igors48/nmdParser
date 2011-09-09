package constructor.objects.output.core;

/**
 * Процессор постобработки сформированного документа
 *
 * @author Igor Usenko
 *         Date: 25.11.2009
 */
public interface ForEachPostProcessor {

    /**
     * Каким-то образом обрабатывает документ в сформированном внешнем файле
     *
     * @param _dir  полное имя каталога с файлом
     * @param _name имя файла
     */
    void process(String _dir, String _name);
}
