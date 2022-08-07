from flask import Flask, request
from torchvision import transforms, models, datasets
import torch
import os, stat
import urllib.request
import json
from torch import nn
from PIL import Image

app = Flask(__name__)

use_pretrained = True
model_ft = models.resnet152(pretrained=use_pretrained)
# Fixed parameter of convolutional kernel so that it will not be included in back propagation
# set_parameter_requires_grad(model_ft, feature_extract)
num_ftrs = model_ft.fc.in_features
# last layer of resnet: 2048 outputs. Change to 34 outputs
model_ft.fc = nn.Sequential(nn.Linear(num_ftrs, 34))

device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")
model_ft.to(device)
check_point = torch.load('./bag_checkpoint_final_v1.pth', map_location=torch.device('cpu'))
best_acc = check_point['best_acc']
model_ft.load_state_dict(check_point['state_dict'])
# model_ft.load_state_dict(torch.load('./bag_checkpoint_v1.pth'))
print(best_acc)
class_names = ['Alana Tote', 'Coach Central Tote With Zip', 'Day Tote', 'Dionysus small GG shoulder bag',
                   'Field Tote', 'GG Marmont', 'GG Marmont matelasse╠ü shoulder bag',
                   'GG Marmont mini top handle bag', 'Gotham Tall Tote', 'Gucci Diana tote bag',
                   'Gucci Horsebit 1955 bag', 'Hero Crossbody', 'Hero Shoulder Bag', 'Jackie 1961 shoulder bag',
                   'Lori Shoulder Bag', 'Mini Cashin Tote', 'Multi Pochette Accessoires', 'Neverfull', 'Onthego',
                   'Ophidia GG small shoulder bag', 'Ophidia medium GG tote', 'Ophidia small GG shoulder bag',
                   'Ophidia small shoulder bag', 'Papillon BB', 'Pillow Tabby Shoulder Bag', 'Soft Tabby Hobo',
                   'Soft Tabby Shoulder Bag', 'Studio Shoulder Bag', 'Tyler Carryall', 'Tyler Carryall 28',
                   'Willow Bucket Bag', 'Willow Shoulder Bag', 'Willow Tote', 'Willow Tote 24']
trans = transforms.Compose([
    transforms.Resize(256),
    transforms.CenterCrop(224),
    transforms.ToTensor(),
    transforms.Normalize([0.485, 0.456, 0.406], [0.229, 0.224, 0.225])  # mean, standard deviation
])

# class AiModel:

# through nginx -- port 8080
base_url = 'http://10.0.0.134:8080/'

def download_image(image_url):
    file_path = './download_images'
    index = image_url.rindex('/')
    print(image_url[index + 1:])
    file_name = image_url[index + 1:]

    try:
        # if path exist
        if not os.path.exists(file_path):
            # create directory
            os.makedirs(file_path)
            # get suffix of images
        file_suffix = os.path.splitext(image_url)[1]
        print(file_suffix)
        # concate image name (including path)
        filename = '{}{}{}'.format(file_path, os.sep, file_name)
        print(filename)
        # download file and save to folder

        urllib.request.urlretrieve(base_url + image_url, filename=filename)
        return filename

    except IOError as e:
        print("IOError")
    except Exception as e:
        print("Exception")
    return ''


@app.route('/ai', methods=['GET'])
def ai():
    image_url = request.args.get('img_url')
    file_name = download_image(image_url)
    img = Image.open(file_name).convert('RGB')
    img_tensor = trans(img).unsqueeze(0)
    print(img_tensor.shape)
    device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")
    global model_ft
    model_ft = model_ft.to(device)
    img_tensor = img_tensor.to(device)
    model_ft.eval()
    with torch.no_grad():
        output = model_ft(img_tensor)
        print(output.shape)
        _, preds_tensor = torch.max(output, 1)
        print(preds_tensor)
        print('predicted: {}'.format(class_names[preds_tensor[0]]))
        data = {"predicted": class_names[preds_tensor[0]]}
    return json.dumps(data)


if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True)