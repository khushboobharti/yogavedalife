variable "profile" {
    default = "default"
}

variable "region" {
    default = "us-east-1"
}

variable "ami" {
    description = "The id for Amazon Linux image"
    type        = string
}

variable "private_key_name" {
    description = "The name for private key on AWS"
    type        = string
}

variable "private_key_path" {
    description = "The filename for private key"
    type        = string
}

variable "docker_username" {
  description = "The docker username"
  type        = string
}

variable "docker_password" {
  description = "The docker password"
  type        = string
}

variable "vpc_security_group_ids" {
  description = "Security group ids array"
  type = list(string)
}

variable "eip_yv" {
  description = "elastic ip"
  type = string
}