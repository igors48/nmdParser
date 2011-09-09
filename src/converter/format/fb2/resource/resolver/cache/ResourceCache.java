package converter.format.fb2.resource.resolver.cache;

import downloader.Data;

/**
 * Кэш ресурсов
 *
 * @author Igor Usenko
 *         Date: 08.11.2009
 */
public interface ResourceCache {
    /**
     * Помещает данные в кэш. Если такие там уже есть - заменяет.
     * Контроллирует размер кэша и удаляет старые элементы если ситуация того требует.
     *
     * @param _url  адрес
     * @param _data данные
     */
    void put(String _url, Data _data);

    /**
     * Возвращает данные из кэша. Если они просроченные - удаляет
     *
     * @param _url адрес
     * @return данные или null если нужных данных в кэше нет или они просроченные
     */
    Data get(String _url);

    /**
     * Делает промежуточный коммит карты кеша
     */
    void commitToc();

    /**
     * Завершает сеанс работы кеша
     */
    void stop();

}
