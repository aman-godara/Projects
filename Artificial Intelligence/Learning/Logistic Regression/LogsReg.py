import numpy as np


def train(data, epoch, learning_rate):
    feature = data['feature']
    actual_class = data['actual_class']
    no_features, no_examples = feature.shape
    hypothesis = dict()
    hypothesis['weights'] = np.zeros((no_features,))
    hypothesis['bias'] = np.array([1])
    temp_out = dict()
    for i in range(epoch):
        forward_propagation(feature, hypothesis, temp_out)
        backward_propagation(feature, hypothesis, temp_out, actual_class, learning_rate)
    return hypothesis


def forward_propagation(feature, hypothesis, temp_out):
    temp_out['first'] = np.dot(hypothesis['weights'], feature) + hypothesis['bias']
    temp_out['second'] = 1 / (1 + np.exp(-1 * temp_out['first']))
    return temp_out


def backward_propagation(feature, hypothesis, temp_out, actual_class, learning_rate):
    update = temp_out['second'] - actual_class
    hypothesis['weights'] = hypothesis['weights'] - (learning_rate * np.dot(update, feature.T))
    hypothesis['bias'] = hypothesis['bias'] - (learning_rate * np.sum(update))


def predict(feature, hypothesis):
    out_forward_prop = forward_propagation(feature, hypothesis, dict())['second']
    out_forward_prop_class = np.zeros(out_forward_prop.shape)
    for i in range(out_forward_prop.shape[0]):
        if out_forward_prop[i] >= 0.5:
            out_forward_prop_class[i] = 1
    return out_forward_prop_class


def binary_classifier():
    test = np.array([[1, 5, 7, 11, 12, 15, 13, 6, 4, 4, 2, 9, 10, 25]])
    data = dict()
    data['feature'] = np.array([[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]])
    data['actual_class'] = np.array([0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1])
    hypothesis = train(data, 500, 0.02)
    prediction = predict(test, hypothesis)
    print('Predicted output for given input is: ', prediction)


binary_classifier()
