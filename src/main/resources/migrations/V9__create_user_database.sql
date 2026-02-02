
CREATE TABLE users (
                          id BIGSERIAL PRIMARY KEY,
                          username VARCHAR(255) NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          Role VARCHAR(255) NOT NULL
);

INSERT INTO users(username, password, Role) VALUES ('mediflowadmin','$2y$05$DGbQh5VBSYvkhVMSEpOUE..oKuvjAGLrelF3.Iu7STCTKorIH4.e6','ADMIN')