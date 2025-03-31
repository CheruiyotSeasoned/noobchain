# NoobChain

NoobChain is a simple blockchain implementation in Java. This project demonstrates the fundamental concepts of blockchain technology, including transactions, wallets, mining, and block validation.

## Features
- **Blockchain Structure**: Implements a basic blockchain with blocks, transactions, and proof-of-work mining.
- **Wallets**: Uses public and private key cryptography for transactions.
- **Transaction System**: Supports sending funds between wallets with validation.
- **Mining**: Implements proof-of-work to add new blocks.
- **Validation**: Ensures blockchain integrity by checking hashes and digital signatures.

## Requirements
- Java 8 or later
- BouncyCastle Security Provider (for cryptography)
- Gson (for JSON serialization)

## Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/NoobChain.git
   cd NoobChain
   ```
2. Compile the project:
   ```sh
   javac -cp ".:lib/bcprov-jdk15on-1.67.jar:lib/gson-2.8.6.jar" org/example/*.java
   ```
3. Run the project:
   ```sh
   java -cp ".:lib/bcprov-jdk15on-1.67.jar:lib/gson-2.8.6.jar" org.example.NoobChain
   ```

## Usage
The program starts by:
1. Creating wallets for two users.
2. Generating a genesis transaction (initial coin supply to a wallet).
3. Mining new blocks and processing transactions between wallets.
4. Validating the blockchain integrity.

Example output:
```
Creating and Mining Genesis block...
WalletA's balance is: 100.0
WalletA is Attempting to send funds (40) to WalletB...
WalletA's balance is: 60.0
WalletB's balance is: 40.0
WalletA Attempting to send more funds (1000) than it has...
Transaction failed due to insufficient funds.
Blockchain is valid.
```

## Blockchain Validation
The `isChainValid()` method checks:
- Hash consistency between blocks.
- Valid proof-of-work difficulty.
- Digital signature validity for transactions.
- Unspent transaction output tracking.

## Project Structure
```
NoobChain/
├── src/
│   ├── org/example/NoobChain.java   # Main class
│   ├── org/example/Block.java       # Blockchain blocks
│   ├── org/example/Transaction.java # Transactions between wallets
│   ├── org/example/Wallet.java      # Digital wallets
│   ├── org/example/StringUtil.java  # Utility functions
│   ├── org/example/TransactionOutput.java # Outputs of transactions
│   ├── org/example/TransactionInput.java  # Inputs of transactions
│
├── lib/
│   ├── bcprov-jdk15on-1.67.jar  # BouncyCastle for cryptography
│   ├── gson-2.8.6.jar           # Gson for JSON serialization
│
├── README.md  # Project documentation
```

## Contributing
Feel free to fork and improve the project. Contributions are welcome!

## License
This project is licensed under the MIT License.

