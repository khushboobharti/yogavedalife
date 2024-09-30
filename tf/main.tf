provider "aws" {
    profile = var.profile
    region = var.region
}

resource "aws_instance" "yv_server" {
    ami = var.ami
    instance_type = "t2.micro"
    key_name = var.private_key_name
    vpc_security_group_ids = var.vpc_security_group_ids

    tags = {
      Name = "yv-instance"
    }

    # Pass environment variables using user_data
    user_data = <<-EOF
              #!/bin/bash
              echo "Setting environment variables"
              echo "export DOCKER_USER=${var.docker_username}" >> /etc/profile
              echo "export DOCKER_PASSWORD=${var.docker_password}" >> /etc/profile
              source /etc/profile
              EOF

    provisioner "file" {
      source = "prod.env"
      destination = "/tmp/prod.env"
    }

    provisioner "file" {
      source = "script_prod.sh"
      destination = "/tmp/script_prod.sh"
    }

    provisioner "file" {
      source = "../scripts/server/update_source.sh"
      destination = "/tmp/update_source"
    }

    provisioner "remote-exec" {
      inline = [
        "chmod +x /tmp/script_prod.sh",
        "sudo /tmp/script_prod.sh"
       ]
    }

    connection {
      host = aws_instance.yv_server.public_ip
      user = "ec2-user"
      private_key = file(var.private_key_path)
    }
}

# Associate an existing Elastic IP with the EC2 instance
resource "aws_eip_association" "eip_yv" {
  instance_id   = aws_instance.yv_server.id
  allocation_id = var.eip_yv  # Replace with your EIP allocation ID
}