package di;

public class BookService {

    @Inject
    BookRepository bookRepository;

    public void join(){
        System.out.println("Service.join");
        bookRepository.save();
    }
}
