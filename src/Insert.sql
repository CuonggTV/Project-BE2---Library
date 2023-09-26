--- Insert loan
insert into loan (bookID,customerID,loanDate,returnDate,status)
values (1,1,"20230512","20231012",0);

-- Insert fine
insert into fine (loanID,customerID,deadline,amount)
values (32,1,"20230512",9);

-- Insert reservation
insert into reservation (bookID,customerID,dateCreated,status)
values (2,2,"20230512",1);