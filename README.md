# AwsService API Documentation

## Setup:

1. **Clone the GitHub repository:**

    ```bash
    git clone https://github.com/your-username/your-repo.git
    cd your-repo
    ```

2. **Install AWS CLI:**

   Make sure you have the AWS CLI installed and configured with Access key ID, Secret access key and Region. If not, you can install it by following the instructions [here](https://aws.amazon.com/cli/).

3. **Update datasource in `application.properties` or add environment variables in a newly created `.env` file in the project root.**

4. **Run the application:**

    ```bash
    ./gradlew clean build bootRun
    ```

---

1. **Discover**

    - **Request**
        - **Endpoint:** `/discover`
        - **Method:** POST
        - **Body:**
            ```json
            ["S3","EC2"]
            ```
        - **Example Response:**
            ```json
            [
                65,
                66
            ]
            ```

2. **Status**

    - **Request**
        - **Endpoint:** `/status/68`
        - **Method:** GET
        - **Example Response:**
            ```json
            "SUCCESS"
            ```

3. **DiscoveryResult**

    - **Request**
        - **Endpoint:** `/discoveryResult/<S3orEC2>`
        - **Method:** GET
        - **Example Response:**
            ```json
            [
                "bucket3",
                "bucket4",
                "bucket2",
                "bucket1"
            ]
            ```

4. **Bucket - List All**

    - **Request**
        - **Endpoint:** `/discover/files/<bucketname>`
        - **Method:** POST

5. **Count Files**

    - **Request**
        - **Endpoint:** `/discover/files/nimesaassignmentbucket2/count`
        - **Method:** GET
        - **Example Response:**
            ```json
            4
            ```

6. **Search Files**

    - **Request**
        - **Endpoint:** `/search`
        - **Method:** GET
        - **Parameters:**
            - `BucketName` (String) - The name of the bucket. Example: `bucket1`
            - `Pattern` (String) - A pattern to search for files. Example: (Leave empty for all files)

    - **Example Response (Pattern empty field):**
        ```json
        [
            "New folder/MQL5 Source File.mq5",
            "New folder/Sample Image.bmp",
            "New folder/Test-File - 2.txt",
            "New folder/Test-File - 3.txt",
            "New folder/Test-File - 4.txt",
            "New folder/Test-File.txt"
        ]
        ```

    - **Example Response (Pattern = .txt):**
        ```json
        [
            "New folder/Test-File - 2.txt",
            "New folder/Test-File - 3.txt",
            "New folder/Test-File - 4.txt",
            "New folder/Test-File.txt"
        ]
        ```
