package constructor.dom;

/**
 * Определение всех возможных конструируемых в программе типов объектов
 *
 * @author Igor Usenko
 *         Date: 21.02.2009
 */
public enum ObjectType {
    /**
     * Хранилище
     */
    STORAGE,
    /**
     * Цепной :) процессор
     */
    PROCESSOR,
    /**
     * Источник модификаций
     */
    SOURCE,
    /**
     * Канал обработки модификаций
     */
    CHANNEL,
    /**
     * Интерпретатор
     */
    INTERPRETER,
    /**
     * Генератор выходного документа
     */
    OUTPUT,
    /**
     * Парсер строкового представления даты
     */
    DATEPARSER,
    /**
     * Сниппет
     */
    SNIPPET,
    /**
     * Симплер
     */
    SIMPLER,

    SAMPLE_OBJECT_01
}