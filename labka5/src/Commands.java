
import java.io.File;
import java.util.ArrayDeque;

/**
 * Интерфейс, описывающий доступные команды для работы с коллекцией
 */
public interface Commands{
   /**Удаляет элемент коллекции
    * @param l коллекция
    * @param e элемент коллекции
    */
    public void remove(Universe<Thing> l, Thing e);
    /** Перечитывает коллекцию из файла
     * @param l коллекция
     */
    public void load(Universe<Thing> l);
    /**Выводит все элементы коллекции
     * @param l коллекция
     */
    public void show(Universe<Thing> l);
    /**Выводит информацию о коллекции(тип,дата,колличество элементов)
     * @param l коллекция
     */
    public void info(Universe<Thing> l);
    /**Добавлет новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
     * @param l коллекция
     * @param e элемент коллекции
     */
    public void addIfMin(Universe<Thing> l, Thing e);
    /**Добавляет новый элемент в коллекцию
     * @param l коллекция
     * @param e элемент коллекции
     */
    public void add(Universe<Thing> l, Thing e);

}
