package com.openclassrooms.Project6Test.ServicesTest;

public class AccountServiceTest {

    /*private AccountService accountService;

    private AccountRepository accountRepository;

    @Before
    public void setup() {

        accountRepository = new AccountRepository() {

            @Override
            public Account findAccountByUserEmail(String email) {
                return null;
            }

            @Override
            public List<Account> findAll() {
                return null;
            }

            @Override
            public List<Account> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<Account> findAllById(Iterable<Integer> iterable) {
                return null;
            }

            @Override
            public <S extends Account> List<S> saveAll(Iterable<S> iterable) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends Account> S saveAndFlush(S s) {
                return null;
            }

            @Override
            public void deleteInBatch(Iterable<Account> iterable) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public Account getOne(Integer integer) {
                return null;
            }

            @Override
            public <S extends Account> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends Account> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<Account> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Account> S save(S s) {
                return null;
            }

            @Override
            public Optional<Account> findById(Integer integer) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Integer integer) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Integer integer) {

            }

            @Override
            public void delete(Account account) {

            }

            @Override
            public void deleteAll(Iterable<? extends Account> iterable) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends Account> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Account> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Account> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Account> boolean exists(Example<S> example) {
                return false;
            }
        };

        accountService = new AccountService(accountRepository);

        String email1 = "queti@queti.com.ar";
        String email2 = "queti@queti.co.uk";

        Account account1 = new Account();
        Account account2 = new Account();

        User user1 = new User();
        User user2 = new User();

        user1.setEmail(email1);
        user2.setEmail(email2);

        account1.setUser(user1);
        account2.setUser(user2);

        accountRepository.save(account1);
        accountRepository.save(account2);
    }

    @Test
    public void getAccountByUserEmail_UserEmailExists_AccountReturned() {

        //Arrange
        String emailToSearch = "queti@queti.com.ar";

        //Act
        Account account = accountRepository.findAccountByUserEmail(emailToSearch);

        //Assert
        assertEquals(emailToSearch, account.getUser().getEmail());
    }*/
}
