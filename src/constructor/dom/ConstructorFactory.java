package constructor.dom;

import java.io.InputStream;

/**
 * ‘абрика конструкторов. ѕрин€то ограничение - "один констурктор - один объект".
 * »спользуетс€ дл€ создани€ отдельных экземпл€ров конструкторов дл€ того,
 * чтобы объекты могли "подгружать" нужные им  по ходу объекты.
 *
 * @author Igor Usenko
 *         Date: 04.03.2009
 */
public interface ConstructorFactory {

    /**
     * —оздает экземпл€р конструктора
     *
     * @return экземпл€р конструктора
     */
    Constructor getConstructor();

    /**
     * —оздает экземпл€р конструктора дл€ считывани€ одного объекта из указанного потока
     *
     * @param _inputStream поток дл€ считывани€
     * @return экземпл€р конструктора
     */
    Constructor getConstructor(InputStream _inputStream);
}