import boto3
import os

def consume_and_ignore_messages(queue_url, max_messages=10, wait_time=20):
    # Create SQS client
    sqs = boto3.client('sqs', region_name='us-east-1')

    while True:
        # Receive message from SQS queue
        response = sqs.receive_message(
            QueueUrl=queue_url,
            AttributeNames=['All'],
            MaxNumberOfMessages=max_messages,
            WaitTimeSeconds=wait_time
        )

        messages = response.get('Messages', [])

        if not messages:
            print("No messages available, waiting...")
            continue

        for message in messages:
            # Print the message ID and message body (optional)
            print(f"Received message: {message}")

            # Delete the message from the queue
            sqs.delete_message(
                QueueUrl=queue_url,
                ReceiptHandle=message['ReceiptHandle']
            )
            print(f"Deleted message: {message}")

        print(f"Processed {len(messages)} messages.")


if __name__ == "__main__":
    # Replace with your SQS queue URL
    queue_url = 'https://sqs.us-east-1.amazonaws.com/255862263184/sandbox-dcp-webhookmanager-queue'
    consume_and_ignore_messages(queue_url, max_messages=1)
