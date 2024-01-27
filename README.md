# AwsService

API Documentation
1. Discover
Request

    Endpoint: /discover
    Method: POST

Body

    Type: JSON
    Content:

    json

    ["S3","EC2"]


2. Status
Request

    Endpoint: /status/68
    Method: GET


3. DiscoveryResult
Request

    Endpoint: /discoveryResult/<S3orEC2>
    Method: GET


4. Bucket - List All
Request

    Endpoint: /discover/files/<bucketname>
    Method: POST


5. Count Files
Request

    Endpoint: /discover/files/nimesaassignmentbucket2/count
    Method: GET

6. Search Files
Request

    Endpoint: /search
    Method: GET

Parameters

    BucketName (String) - The name of the bucket.
    Example: bucket1

    Pattern (String) - A pattern to search for files.
    Example: (Leave empty for all files)
