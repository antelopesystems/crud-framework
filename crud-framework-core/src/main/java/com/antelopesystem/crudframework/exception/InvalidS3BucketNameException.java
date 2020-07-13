package com.antelopesystem.crudframework.exception;

public class InvalidS3BucketNameException extends RuntimeException {

	public InvalidS3BucketNameException(String bucketName) {
		super("Bucket Name [ " + bucketName + " ] is not valid by S3 standards");
	}
}

