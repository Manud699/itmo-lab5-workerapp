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


public class WorkerRepository {

    private final Deque<Worker> workers;
    private final ZonedDateTime creationDate; 
    private  FormSave formSave; 
    private  FormLoad formLoad; 


    public WorkerRepository() { 
        this.workers = new ArrayDeque<>();
        this.creationDate = ZonedDateTime.now();
    }



    public boolean isCollectionEmpty(){
        return workers.isEmpty();
    }



    public void load() {
        formLoad.load();
        WorkerIdGenerator.syncWithExistingWorkers(workers);
    } 



    public void updateWorkerById(Worker workerUpdated) {
        workers.add(workerUpdated);
    }



    public void save(Deque<Worker> workers) {
        formSave.save();
    } 



    public Deque<Worker> getWorkers() {
        return workers; 
    } 



    public boolean add(Worker worker) {    
        return workers.add(worker);  
    }



    public ZonedDateTime getCreationDate() {
        return creationDate; 
    } 



    public List<Worker> getWorkersSortedById() {
        return workers.stream().sorted(Comparator.comparing(Worker::getId)).collect(Collectors.toList());
    } 



    public String getTipeCollection() {
        return workers.getClass().getSimpleName();
    }



    public int getSize() {
        return workers.size(); 
    } 



    public void show(Console console){
        workers.stream().map(Object::toString).forEach(console::println);
    } 



    public void clear() {
        workers.clear();
    } 



    public boolean removeById(long id) { 
        return workers.removeIf(x -> x.getId() == id);  
    }



    public Worker getHead(){
        return (!workers.isEmpty()) ? workers.getFirst() : null;  
    }   



    public Worker removeHead() {
        return (!workers.isEmpty()) ? workers.removeFirst() : null; 
    } 



    public long sumOfSalary() {
        return workers.stream().mapToLong(Worker::getSalary).sum(); 
    }



    public boolean isWorkerId(long workerId) {
        return workers.stream().anyMatch(worker -> worker.getId()== workerId);
    } 



    public void printFieldDescendingSalary(Console console) {
        workers.stream().map(Worker::getSalary).sorted(java.util.Comparator.reverseOrder()).forEach(salary -> console.println(" > " + salary));;
    }



    public Map<Long, Worker> getWorkerMap() {
        return workers.stream().collect(Collectors.toMap(Worker::getId, worker -> worker));
    }



    public void setFormLoad(FormLoad formLoad) {
        this.formLoad = formLoad;
    } 



    public void setFormSave(FormSave formSave) {
        this.formSave= formSave;
    } 
}






