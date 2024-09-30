# Terraform production variables
terraform_prod.tfvars

# Terraform dev variables
terraform_dev.tfvars

# Execute terraform with specific tfvars file
terraform plan --out=tfplan.out -var-file="terraform_dev.tfvars"