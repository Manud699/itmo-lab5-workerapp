package workerapp.repository;

import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import workerapp.cli.Console;
import workerapp.model.Worker;
import workerapp.model.WorkerIdGenerator;
import workerapp.repository.file.FormLoad;
import workerapp.repository.file.FormSave;

/**
 * WorkerRepository is responsible for CRUD operations on the collection of Worker objects.
 */
public class WorkerRepository {

    private final Deque<Worker> workers;
    private final ZonedDateTime creationDate; 
    private  FormSave formSave; 
    private  FormLoad formLoad; 


    public WorkerRepository() { 
        this.workers = new ArrayDeque<>();
        this.creationDate = ZonedDateTime.now();
    }


    /**
     * @return true if the collection is empty, false otherwise
     */
    public boolean isCollectionEmpty(){
        return workers.isEmpty();
    }



    /**
     * @param workerUpdated the Worker object with updated information
     */
    public void updateWorkerById(Worker workerUpdated) {
        workers.add(workerUpdated);
    }



    /**
     * Loads the worker data from the form load.
     */
    public void load() {
        formLoad.load();
        WorkerIdGenerator.syncWithExistingWorkers(workers);
    }



    /**
     * Saves the worker data using the form save.
     */
    public void save() {
        formSave.save();
    } 



    /**
     * @return the collection of Worker objects
     */
    public Deque<Worker> getWorkers() {
        return workers; 
    } 



    /**
     * @param worker the Worker object to add
     * @return true if the worker was added, false otherwise
     */
    public boolean add(Worker worker) {    
        return workers.add(worker);  
    }



    /**
     * @return the creation date of the collection
     */
    public ZonedDateTime getCreationDate() {
        return creationDate; 
    } 



    /**
     * @return a list of Worker objects sorted by their IDs
     */
    public List<Worker> getWorkersSortedById() {
        return workers.stream().sorted(Comparator.comparing(Worker::getId)).collect(Collectors.toList());
    } 


    /**
     * @return the type of the collection as a string
     */
    public String getTipeCollection() {
        return workers.getClass().getSimpleName();
    }



    /**
     * @return the number of Worker objects in the collection
     */
    public int getSize() {
        return workers.size(); 
    } 



    /**
     * @param console the Console object to use for output
     */
    public void show(Console console){
        workers.stream().map(Object::toString).forEach(console::println);
    } 



    /**
     * Clears the collection of Worker objects.
     */
    public void clear() {
        workers.clear();
    } 



    /**
     * @param id the ID of the Worker to remove
     * @return true if a Worker with the specified ID was removed, false otherwise
     */
    public boolean removeById(long id) { 
        return workers.removeIf(x -> x.getId() == id);  
    }



    /**
     * @return the first Worker in the collection, or null if the collection is empty
     */
    public Worker getHead(){
        return (!workers.isEmpty()) ? workers.getFirst() : null;  
    }   



    /**
    * @return the first Worker in the collection and removes it, or null if the collection is empty
    */
    public Worker removeHead() {
        return (!workers.isEmpty()) ? workers.removeFirst() : null; 
    } 



    /**
     * @return the sum of the salaries of all Worker objects in the collection
     */
    public long sumOfSalary() {
        return workers.stream().mapToLong(Worker::getSalary).sum(); 
    }



    /**
     * @param workerId the ID of the Worker to check
     * @return true if a Worker with the specified ID exists in the collection, false otherwise
     */
    public boolean isWorkerId(long workerId) {
        return workers.stream().anyMatch(worker -> worker.getId()== workerId);
    } 



    /**
     * @param console the Console object to use for output
     */
    public void printFieldDescendingSalary(Console console) {
        workers.stream().map(Worker::getSalary).sorted(java.util.Comparator.reverseOrder()).forEach(salary -> console.println(" > " + salary));;
    }



    /**
     * @return a map of Worker objects indexed by their IDs
     */
    public Map<Long, Worker> getWorkerMap() {
        return workers.stream().collect(Collectors.toMap(Worker::getId, worker -> worker));
    }



    /**
     * Sets the form load object.
     * @param formLoad the FormLoad object to set
     */
    public void setFormLoad(FormLoad formLoad) {
        this.formLoad = formLoad;
    } 



    /**
     * Sets the form save object.
     * @param formSave the FormSave object to set
     */
    public void setFormSave(FormSave formSave) {
        this.formSave= formSave;
    } 
}






